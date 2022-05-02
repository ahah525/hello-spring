package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 1. JpaRepository를 extends 하는 인터페이스를 만들어 놓으면
// 2. 스프링 데이터 JPA가 인터페이스 구현체를 자동 생성하고 스프링 빈에 자동 등록함
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // JPQL select m from Member m where m.name = ?
    Optional<Member> findByName(String name);
}
