package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    // 엔티티를 관리하는 객체
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // member를 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // DB에서 pk가 id인 member 조회(식별자로 조회)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // member 객체를 Optional로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 엔티티 객체 member의 name이 name인 member 조회
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();   // member 리스트 객체를 Optional로 반환
    }

    @Override
    public List<Member> findAll() {
        // 엔티티 객체 member의 모든 member 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
