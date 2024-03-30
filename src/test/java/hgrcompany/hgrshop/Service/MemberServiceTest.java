package hgrcompany.hgrshop.Service;

import hgrcompany.hgrshop.domain.Member;
import hgrcompany.hgrshop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 스프링 통합 테스트
@SpringBootTest
@Transactional // 데이터 변경 - 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // @Autowired EntityManager em; 테스트 과정에서 인서트문 보는 방법
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("HGR");
        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush(); // 실제로 flush하여 인서트문 출력.
        // 실제 디비에서는 테스트가 끝난 후 롤백해준다.
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("hgr1");

        Member member2 = new Member();
        member2.setName("hgr1");
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 함

        //then
        fail("예외가 발생해야 한다."); // 예외가 발생하지 않아 리턴되지 않은 경우
    }
}