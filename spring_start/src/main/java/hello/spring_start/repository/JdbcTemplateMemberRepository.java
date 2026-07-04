package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
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

public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    // JDBC를 더 쉽고 편리하게 사용할 수 있도록 도와주는 클래스
    // JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다.

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // SimpleJdbcInsert 클래스: 복잡한 SQL문을 직접 작성하지 않아도 데이터를 삽입하는 작업을 단순화해준다
        // jdbcTemplate을 생성자에 넣어 데이터베이스 연결 정보를 공유
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        // 데이터를 저장할 테이블 이름을 member로 지정, 1,2,3.. 번호가 생기는 col의 이름을 id로 짓는 동시에 데이터를 넣을 때마다 id 칸에 들어간 값을 알려달라고 요청
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // executeAndReturnKey: 위에서 설정한 방법대로 id 값을 찾아서 가져온다
        // db가 얼마나 큰 숫자를 줄지 모르니 숫자를 담을 수 있는 상자 Number를 준비
        // MapSqlParameterSource: db에 넣을 데이터를 담아두는 전용 박스
        // SimpleJdbcInsert를 사용할 때, 어떤 데이터를 넣을지 그 내용물을 담아서 전달하기 위해 사용
        // db에 보낼 정보를 Map에 담아 MapSqlParameterSource 가방에 넣는다
        member.setId(key.longValue());
        // member 객체에 id를 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        // 세번째 인자는 ?에 넣을 진짜 값
        return result.stream().findAny();
        // 조건에 맞는거 아무거나 하나만 찾으라는 뜻
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }
    // query: db에서 여러 개의 데이터(행)을 조회할 때 사용하는 메소드
    // (db에게 날릴 질문 sql문, db가 보낸 데이터를 객체로 번역할 함수)

    private RowMapper<Member> memberRowMapper() {
        // RowMapper: db의 row를 하나 꺼내서 Member 객체로 바꾸는 인터페이스
        return (rs, rowNum) -> {
            // rs: db에서 가져온 데이터 한줄, rowNum: 몇번째 줄인지 나타내는 숫자
            Member member = new Member();
            member.setId(rs.getLong("id"));
            // id라는 이름의 컬럼값을 꺼내서 객체에 넣는다
            member.setName(rs.getString("name"));
            // name이라는 이름의 컬럼값을 꺼내서 객체에 넣는다
            return member;
            // 이름과 id가 채워진 member 객체를 내보낸다
        };
    }
}


