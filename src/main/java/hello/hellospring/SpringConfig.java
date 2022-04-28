package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    // 스프링 빈에 등록
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());   // 의존 관계 설정
    }
    // 나중에 DB가 정해지면 바뀔 부분
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
