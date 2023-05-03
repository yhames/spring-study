package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    @DisplayName("crud")
    void crud() throws Exception {
        // create
        Member member = new Member("memberV3", 10000);
        repository.save(member);

        // read
        Member findMember = repository.findById(member.getMemberId());
        // 참조값이 아니라 실제 값이 보이는 이유는 롬복의 @Data 에서 toString()을 자동으로 생성해서
        log.info("findMember={}", findMember);
        // 비교가 가능한 것은 롬복의 @Data 에서 EqualsAndHashCode()를 자동으로 생성해서
        assertThat(findMember).isEqualTo(member);

        // update
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        assertThrows(NoSuchElementException.class,
                () -> repository.findById(member.getMemberId()));
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}