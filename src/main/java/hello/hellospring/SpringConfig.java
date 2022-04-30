package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    // SpringConfig가 EntityManger 의존(생성자 주입)
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    // 스프링 빈에 등록
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());   // 의존 관계 설정
    }
    // 나중에 DB가 정해지면 바뀔 부분
    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);    // JdbcMemberRepository가 dataSource 의존
        //return new JdbcTemplateMemberRepository(dataSource); // JdbcTempalteRepository가 dataSource 의존
        return new JpaMemberRepository(em); // JpaMemberRepository가 EntityManager 의존
    }

}
