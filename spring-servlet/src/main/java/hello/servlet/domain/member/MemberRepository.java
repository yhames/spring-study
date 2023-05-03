package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashMap은 동시성 문제 때문에, 실무에서는 ConcurrentHashMap, AtomicLong 사용한다.
 */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * 스프링 컨테이너를 사용하지 않고 순수 Servlet 으로 구현하기 위해 싱글톤으로 생성
     * 이펙티브자바 ITEM3 : private 생성자나 열거 타입으로 싱글턴임을 보증하라.
     */
    // static 으로 MemberRepository 인스턴스를 생성
    private static final MemberRepository instance = new MemberRepository();

    // MemberRepository 객체에 접근하기 위해 인스턴스를 반환하는 *정적 팩토리 메서드* 구현
    public static MemberRepository getInstance() {
        return instance;
    }

    // 생성자를 private 으로 선언하여 오직 내부에서만 객체 생성
    private MemberRepository() {
    }


    /**
     * 핵심 메서드 구현
     */
    public Member save(Member member) {
        member.setId(sequence++);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
