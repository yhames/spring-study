package hello.jdbc.domain;

import lombok.Data;

@Data   // Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value 자동 생성
public class Member {
    private String memberId;
    private int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
