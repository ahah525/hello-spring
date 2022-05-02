package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    // 스프링 데이터 JPA가 만든 인터페이스 구현체(memberRepository) 자동 주입
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // memberService 스프링 빈 등록
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);   // memberService가 memberRepository 의존
    }
    // 나중에 DB가 정해지면 바뀔 부분
//    @Bean
//    public MemberRepository memberRepository() {
//        //return new MemoryMemberRepository();
//        //return new JdbcMemberRepository(dataSource);    // JdbcMemberRepository가 dataSource 의존
//        //return new JdbcTemplateMemberRepository(dataSource); // JdbcTempalteRepository가 dataSource 의존
//        //return new JpaMemberRepository(em); // JpaMemberRepository가 EntityManager 의존
//    }

}
