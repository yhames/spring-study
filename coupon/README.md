# Coupon Issuance Service

- Coupon Core
  : Coupon API와 Coupon Consumer에서 공통적으로 사용하는 기능

- Coupon API
  : 쿠폰에 대한 API 서버, 유저에 대한 요청을 처리

- Coupon Consumer
  : 비동기적으로 발급되는 쿠폰 목록을 읽어서 실제 쿠폰 발급을 처리

## 동시성 문제 해결

### 트랜잭션과 synchronized

> Java의 synchronized 키워드를 사용하여 락 구현

- 트랜잭션 메서드 내부에서 `synchronized`를 사용하는 경우
  : 트랜잭션이 커밋되기 전에 락을 해제하기 때문에 트랜잭션 커밋 전 동시성 문제가 발생할 수 있음.

- `Scale-out` 환경에서 동시성 문제를 해결할 수 없음.

### Redis Lock

> Redis는 분산 환경에서 동시성 문제를 해결하기 위한 분산 락을 제공 (Redisson)

- Redisson 라이브러리를 사용하는 이유
  : redisson는 분산락 인터페이스를 제공하고(`RLock`), 락을 획득할 때 `tryLock` 메서드를 사용하여 락을 획득할 수 있는지 확인 가능.
  : 또한 lettuce와 다르게 스핀 락을 사용하지 않고, pub/sub을 사용하여 락을 획득

- `DistributeLockExecutor`
  : 템플릿 메서드 패턴을 사용하여 락 획득 및 해제를 자동으로 처리

```java

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributeLockExecutor {

    private final RedissonClient redisson;

    public void execute(String lockName, long waitMilliSecond, long leaseMilliSecond, Runnable logic) {
        RLock lock = redisson.getLock(lockName);
        try {
            boolean isLocked = lock.tryLock(waitMilliSecond, leaseMilliSecond, TimeUnit.MILLISECONDS);
            if (!isLocked) {
                throw new IllegalStateException("[" + lockName + "] Lock acquisition failed.");
            }
            logic.run();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
```

```java
    public void issueCoupon(CouponIssueRequestDto request) {
    distributeLockExecutor.execute("lock_" + request.couponId(), 10000, 10000,
            () -> couponIssueService.issue(request.couponId(), request.userId()));
    log.info("쿠폰 발급 완료: couponId={}, userId={}", request.couponId(), request.userId());
}
```

### Record Lock

> RDBMS에서 제공하는 레코드 락을 사용하여 동시성 문제 해결  
> JPA의 `@Lock` 어노테이션을 사용하여 레코드 락을 획득할 수 있음

- 레코드 락
  : 특정 레코드에 대한 락을 획득하여 다른 트랜잭션이 해당 레코드를 조회하거나 수정하지 못하도록 함.
  : `SELECT ... FOR UPDATE` 쿼리를 사용하여 레코드 락을 획득
  : 트랜잭션이 커밋될 때 락이 해제됨.

- JPA에서 레코드 락을 획득하는 방법
  : `@Lock` 어노테이션을 사용하여 레코드 락을 획득할 수 있음.
  : `LockModeType.PESSIMISTIC_WRITE`를 사용하여 레코드 락을 획득

- Mysql에서 병목 발생
  : 복잡한 인프라 없이 트랜잭션 단위의 ACID를 보장할 수 있지만,
  : 레코드 락으로 인해 병목 현상이 발생할 수 있음.

```java
public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)   // Transaction이 커밋될 때 Lock이 해제됨
    @Query("SELECT c FROM Coupon c WHERE c.id = :id")
    Optional<Coupon> findCouponByIdWithLock(long id);
}
```

### Redis Script

> Redis 분산 Lock을 사용하면 동시성 문제를 해결할 수 있지만,
> 분산 Lock에 병목이 발생하게 된다.

가장 효율적이 방법은 `Redis Script`를 사용하여 동시성 문제를 해결하는 것이다.

- Redis Script
  : `Lua 스크립트`를 사용하여 Redis에서 여러 명령어를 동시에 원자적으로 실행되는 명령어를 작성할 수 있음.
  : Redis는 `Lua 스크립트`를 실행할 때, 스크립트가 실행되는 동안 다른 클라이언트의 명령어를 차단함.
  : 이를 통해 동시성 문제를 해결할 수 있음.

```java

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private RedisScript<String> issueRequestScript() {
        String script = """
                if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                    return '2'
                end
                
                if tonumber(ARGV[2]) <= redis.call('SCARD', KEYS[1]) then
                    return '3'
                end
                
                redis.call('SADD', KEYS[1], ARGV[1])
                redis.call('RPUSH', KEYS[2], ARGV[3])
                return '1'
                """;
        return RedisScript.of(script, String.class);
    }
}
```

```java

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    public void issueRequest(long couponId, long userId, int totalQuantity) {
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId, userId);
        try {
            String code = redisTemplate.execute(
                    issueScript,
                    List.of(getIssueRequestKey(couponId), issueRequestQueueKey),
                    String.valueOf(userId),
                    String.valueOf(totalQuantity),
                    objectMapper.writeValueAsString(couponIssueRequest)
            );
            checkRequestResult(CouponIssueRequestCode.of(code));
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(ErrorCode.FAILED_COUPON_ISSUE_REQUEST,
                    "input: %s".formatted(couponIssueRequest));
        }
    }
}
```

## 트러블슈팅

<details>

<summary>
Locust에서 Host의 8080포트로 요청이 안되는 경우
</summary>

```yaml
services:
  master:
    image: locustio/locust
    ports:
      - "8089:8089"
    volumes:
      - ./:/mnt/locust
    command: -f /mnt/locust/locustfile-hello.py --master -H http://host.docker.internal:8080
```

load-test의 `docker-compose.yml`에서 부하테스트시 `host.docker.internal`를 사용합니다.

Windows나 MacOS에서는 `host.docker.internal`이 자동으로 설정되지만, Linux에서는 Docker가 호스트의 IP 주소를 자동으로 인식하지 못합니다.

따라서, `host.docker.internal`을 사용하려면 다음과 같이 설정해야 합니다:

```yaml
services:
  master:
    image: locustio/locust
    ports:
      - "8089:8089"
    volumes:
      - ./:/mnt/locust
    command: -f /mnt/locust/locustfile-hello.py --master -H http://host.docker.internal:8080
    extra_hosts:
      - "host.docker.internal:host-gateway"
```

</details> 


<details>

<summary>
WSL2에서 Host의 8080포트로 요청이 안되는 경우
</summary>

```yaml
services:
  master:
    image: locustio/locust
    ports:
      - "8089:8089"
    volumes:
      - ./:/mnt/locust
    command: -f /mnt/locust/locustfile-hello.py --master -H http://host.docker.internal:8080
    extra_hosts:
      - "host.docker.internal:host-gateway"
```

load-test에서 host-gateway를 사용하면 WSL2의 IP 주소를 자동으로 설정합니다.

WSL2는 별도의 IP 주소를 갖기 때문에, Windows에서 실행한 coupon-api 서버에 접속할 수 없습니다.

따라서 load-test의 루트 디렉토리에 `.env` 파일을 생성하고 다음 내용을 추가합니다:

```env
HOST_IP=<Windows의 IP 주소>
```

그리고 `docker-compose.yml` 파일에서 `extra_hosts`를 다음과 같이 수정합니다:

```yaml
services:
  master:
    image: locustio/locust
    ports:
      - "8089:8089"
    volumes:
      - ./:/mnt/locust
    command: -f /mnt/locust/locustfile-hello.py --master -H http://host.docker.internal:8080
    extra_hosts:
      - "host.docker.internal:${HOST_IP}"
```

</details> 

