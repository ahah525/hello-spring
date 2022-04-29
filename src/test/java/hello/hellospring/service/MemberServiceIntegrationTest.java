package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    // 이전처럼 객체를 직접 생성하는 것이 아니라 스프링 컨테이너에게 멤버 리퍼지토리, 멤버 서비스 요청
    @Autowired MemberService memberService;    // 회원 서비스
    @Autowired MemberRepository memberRepository;    // 메모리 회원 리퍼지토리

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
    }

}