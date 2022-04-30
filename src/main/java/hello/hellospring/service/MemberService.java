package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    // 메모리 회원 리퍼지토리 직접 생성
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    // 외부에서 받아온 MemberRepository를 넣어주는 방식으로 변경

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름의 회원은 중복 회원 가입X
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);  // 중복 회원 아니면 저장
        return member.getId();  // 저장한 member id 반환
    }
    // 파라미터로 들어온 member가 메모리에 이미 존재하는 회원인지 검증하는 함수
    private void validateDuplicateMember(Member member) {
        // optional 안의 값이 있으면(null이 아님) 예외 처리
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     *  전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    // 해당 id에 해당하는 회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
