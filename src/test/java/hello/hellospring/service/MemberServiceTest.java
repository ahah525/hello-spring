package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;    // 회원 서비스
    MemoryMemberRepository memberRepository;    // 메모리 회원 리퍼지토리

    // 테스트가 독립적으로 실행되기 위해 서비스, 리퍼지토리 각각 생성되도록함
    @BeforeEach
    public void beforeEach() {
        // 같은 메모리 레퍼지토리가 사용됨
        memberRepository = new MemoryMemberRepository();
        // 위에서 생성한 MemoryMemberReposiotory를 MemberService의 MemberRepository에 넣어줌
        memberService = new MemberService(memberRepository);
    }

    // 테스트가 끝날 때마다 메모리 비우기
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        // 우리가 저장한 것이 repository에 저장되었는지 검사
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());

        /*

         */
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // Given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);    // member1 회원가입
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // member2 회원가입 시 예외 발생
        // 위에서 발생한 예외가 중복 회원 가입 예외인지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        // when을 try-cath로 구현
         try {
             memberService.join(member2);
             fail();
         } catch (IllegalStateException e) {
             // 예외가 터졌을 때 중복 회원 가입 예외인지 검증
             assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
         }
         */

    }

}