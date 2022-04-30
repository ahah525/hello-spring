package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    // 생성자가 1개면 @Autowired 생략O
    // 스프링이 자동으로 dataSource 인젝션을 해줌
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 파라미터로 들어온 member를 DB에 삽입하는 함수
    @Override
    public Member save(Member member) {
        // insert 문 자동 생성
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // table 명과 pk
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    // 해당 id를 가진 member를 Optional로 반환하는 함수
    @Override
    public Optional<Member> findById(Long id) {
        // DB의 id(column)이 파라미터 id 값과 같은 member를 찾은 결과를 리스트로 저장
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();   // 리스트에 있는 member 값을 Optional로 반환
    }

    // 해당 name을 가진 member를 Optional로 반환하는 함수
    @Override
    public Optional<Member> findByName(String name) {
        // DB의 name(column)이 파라미터 name 값과 같은 member를 찾은 결과를 리스트로 저장
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();   // 리스트에 있는 member 값을 Optional로 반환
    }

    // DB에 저장된 모든 member들을 리스트로 반환하는 함수
    @Override
    public List<Member> findAll() {
        // DB에 저장된 모든 member를 리스트로 반환
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // 쿼리 결과를 RowMapper로 맵핑해주는 함수
    // 쿼리 결과 값(로우값)들을 RowMapper를 이용해 ResultSet을 자바 객체로 변환
    private RowMapper<Member> memberRowMapper() {
        // 람다식으로 변경
        return (rs, rowNum) -> {
            // member 객체를 생성해 쿼리에서 가져온 결과(resultSet)를 맵핑
            Member member = new Member();
            member.setId(rs.getLong("id")); // resultSet의 column이 id인 값을 long으로 가져와 id에 대입
            member.setName(rs.getString("name"));   // resultSet의 column이 name인 값을 String으로 가져와 name에 대입
            return member;  // member 객체 반환
        };
        /*
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        }
         */
    }
}
