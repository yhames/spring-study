# Coupon Issuance Service

- Coupon Core
  : Coupon API와 Coupon Consumer에서 공통적으로 사용하는 기능

- Coupon API
  : 쿠폰에 대한 API 서버, 유저에 대한 요청을 처리

- Coupon Consumer
  : 비동기적으로 발급되는 쿠폰 목록을 읽어서 실제 쿠폰 발급을 처리

## 동시성 문제 해결

### 트랜잭션과 synchronized

- 트랜잭션 메서드 내부에서 `synchronized`를 사용하는 경우
  : 트랜잭션이 커밋되기 전에 락을 해제하기 때문에 트랜잭션 커밋 전 동시성 문제가 발생할 수 있음.

- `Scale-out` 환경에서 동시성 문제를 해결할 수 없음.

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

