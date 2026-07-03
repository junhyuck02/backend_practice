package hello.spring_start;

// 자바 코드로 직접 스프링 빈 등록하기

import hello.spring_start.repository.JdbcMemberRepository;
import hello.spring_start.repository.MemberRepository;
import hello.spring_start.repository.MemoryMemberRepository;
import hello.spring_start.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
// 여기에 스프링 빈을 직접 등록하는 설정 코드를 작성할 테니 여기서 빈을 찾아서 등록해달라고 요청
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    // 이 메서드에서 나오는 결과물(객체)을 spring 컨테이너에 담아서 빈으로 관리하라고 요청
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
