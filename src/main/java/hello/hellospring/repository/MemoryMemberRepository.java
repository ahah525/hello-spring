package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemoryMemberRepository implements MemberRepository {
    // Map<key: Member의 id, value: Member>
    private static Map<Long, Member> store = new HashMap<>();   // 회원 저장 공간
    private static long sequence = 0L;  // 시스템에서 Member의 id를 자동으로 넣어주기 위함

    // (id, member)를 메모리에 저장하고 member를 반환하는 함수
    @Override
    public Member save(Member member) {
        member.setId(++sequence);   // id 값을 자동으로 1개 증가
        store.put(member.getId(), member);  // (member의 id, member)를 map에 넣음
        return member;
    }

    // key가 id인 member를 반환하는 함수
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  // member가 null일 수도 있으니 optional로 감싸기
    }

    // 메모리에 저장된 모든 member(value)들을 반환하는 함수
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store의 member들 반환
    }

    // 메모리에 저장된 회원들 중 파라미터(name)과 같은 member의 name을 가진 member를 반환하는 함수
    @Override
    public Optional<Member> findByName(String name) {
        // 데이터소스객체집합.steam생성.중개연산.최종연산
        // stream에서 filter(조건)을 만족하는 어떤 요소 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    // 메모리에 저장된 모든 회원 정보(id, member)를 삭제하는 함수
    public void clearStore() {
        store.clear();
    }
}
