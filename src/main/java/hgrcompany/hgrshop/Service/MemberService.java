package hgrcompany.hgrshop.Service;

import hgrcompany.hgrshop.domain.Member;
import hgrcompany.hgrshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 스프링에서 제공하는 어노테이션 사용
// 클래스 레벨에서의 어노테이션을 사용하면
// 클래스 내부의 모든 메소드에 트랜젝션 적용됨.
// 이 클래스의 경우 읽기가 많으므로 클래스 레벨에서 리드온리.
@RequiredArgsConstructor // final 필드를 대상으로 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // 회원가입은 읽기가 아님. 리드온리를 빼자.
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 익셉션
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) { // 이미 회원이 있으면 터져야 함
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } // DB 레벨에서 멤버에 유니크 제약조건을 걸어줘야 한다. (동시성 해결)
    }
    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
