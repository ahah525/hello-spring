package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    // 메모리 리퍼지토리 구현체 생성
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 각 테스트가 끝날 때마다 실행
    @AfterEach
    public void afterEach(){
        repository.clearStore();    // 메모리에 있는 모든 member들을 삭제함(테스트 순서에 의존관계를 없애기 위해)
    }

    // 메모리에 member가 저장이 제대로 되는지 검증하는 함수
    @Test
    public void save() {
        // given (name이 spring인 member가 있을 때)
        Member member = new Member();
        member.setName("spring");

        // when (member를 메모리에 저장)
        repository.save(member);

        // then (메모리에 member가 잘 저장되었는지 검증)
        Member result = repository.findById(member.getId()).get();  // optional안의 member 객체 반환
        // 메모리에서 가져온 member가 위에서 생성한 member와 같은지 검증
        assertThat(result).isEqualTo(member);
    }

    // 메모리에 member가 저장이 제대로 되는지 검증하는 함수
    @Test
    public void findByName() {
        // given (메모리에 name이 spring1, spring2인 member 2개가 저장되어 있을 떄)
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when (메모리에서 name이 spring1인 member 조회)
        Member result = repository.findByName("spring1").get(); // optional 안의 member 객체 반환

        //then (메모리에서 name에 해당하는 member가 잘 조회되는지 검증)
        // 메모리에서 name이 spring1인 member가 member1이 맞는지 검증
        assertThat(result).isEqualTo(member1);
    }

    // 메모리의 모든 member들이 제대로 조회되는지 검증
    @Test
    public void findAll() {
        // given (메모리에 name이 spring1, spring2인 member 2개가 저장되어 있을 떄)
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when (메모리에 있는 모든 member 조회)
        List<Member> result = repository.findAll(); // 메모리의 모든 member들을 list로 반환

        // then (메모리의 모든 member들이 제대로 조회되는지 검증)
        // 메모리에 있는 모든 member 수와 위에서 저장한 member 수가 같은지 검사
        assertThat(result.size()).isEqualTo(2); // result.size()가 2와 맞는지 검사
    }

}

