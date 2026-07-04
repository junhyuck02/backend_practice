package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 회원을 저장하는 구현을 db랑 연동해서 jdbc로
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;

    // DataSource: db와 대화하기 위한 연결 통로를 관리하는 인터페이스
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        // member 테이블에 새로운 데이터를 추가하기 위한 SQL 쿼리문

        // 데이터를 주고받는 과정에서 필요한 필수 도구들
        Connection conn = null;
        // Connection: 자바와 db의 연결을 나타내는 객체, db로 들어가는 열쇠
        PreparedStatement pstmt = null;
        // PreparedStatement: SQL 쿼리문을 db로 전달하는 실행 도구, db에게 전달하는 질문지
        ResultSet rs = null;
        // ResultSet: SELECT 문을 실행한 후, 그 결과 데이터를 담고 있는 표, db가 질문지를 읽고 보내준 답안지
        try {
            conn = getConnection();
            // getConnection(): db와 실제 연결을 맺고 그 연결 정보를 담고 있는 Connection 객체
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 데이터를 추가하고 그 결과로 생성된 기본 키 값을 다시 받아오고 싶을 때 사용
            pstmt.setString(1, member.getName());
            // sql에 정의된 ? 자리에 set으로 실제 값을 채워 넣는 과정 - ?이 여러개면 순서대로
            pstmt.executeUpdate();
            // db의 상태를 변경하는 sql을 실행할 때 사용, 몇개의 줄이 변경되었는지 알려줌
            rs = pstmt.getGeneratedKeys();
            // 방금 db가 만든 새 식별 번호(ID)를 알려달라고 요청
            if (rs.next()) {
                // 결과 표에 다음 줄이 있는가?
                member.setId(rs.getLong(1));
                // 행의 첫 번째 열 값을 long 타입으로 가져와라
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            // 정리하고 연결 통로를 반환
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        // 특정 조건을 만족하는 회원을 조회하기 위한 SQL 쿼리문
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            // 인자가 하나면 이 SQL 문 안에는 나중에 값을 끼워 넣을 ?가 없거나, 지금 당장은 값을 넣지 않고 나중에 넣겠다 라는 뜻
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            // db로부터 정보를 읽어올 때(조회) 사용, 데이터 값들을 반환함
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
                // of: 무조건 값이 있다고 생각할 때 객체를 감싸는 박스
            } else {
                return Optional.empty();
                // empty: 박스가 비어있다는 걸 표현할 때 사용
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        // db와 연결을 더 안전하고 효율적으로 관리하고 동기화하기 위해 DataSourceUtils를 사용
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
        // db와의 연결을 더 안전하게 해제
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}