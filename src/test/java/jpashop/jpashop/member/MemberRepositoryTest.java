package jpashop.jpashop.member;

import jpashop.jpashop.domain.Member;
import jpashop.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test @Transactional
    @Rollback(value = false)
    public void testMember() {
        Member member = new Member();
        member.setName("memberA");

        Long saveId = memberRepository.save(member);

        Member findMember = memberRepository.findOne(saveId);

        assertThat(member.getId()).isEqualTo(findMember.getId());
    }

}