package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// JPA가 관리하는 엔티티
@Entity
public class Member {
    // pk 맵핑, 자동 생성
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;        // 단순히 데이터 구분을 위해 사용(실제 회원 id가 아님)
    private String name;    // 회원 이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
