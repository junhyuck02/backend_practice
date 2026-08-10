package hello.spring_basic;

import hello.spring_basic.member.Grade;
import hello.spring_basic.member.Member;
import hello.spring_basic.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


// 회원 도메인 - 회원 가입 main
// 이렇게 테스트 하는건 좋은 방법이 아니니 테스트를 사용하자
public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // MemberApp에 스프링 컨테이너 적용

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // AppConfig 클래스에 정의된 @Bean 설정을 읽어서 스프링 컨테이너(ApplicationContext)를 생성한다
        // AnnotationConfigApplicationContext: ApplicationContext 인터페이스를 실제 코드로 구현한 실제 클래스
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // 컨테이너에서 꺼내오기, 빈의 이름과 그 해당 타입을 인자로

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
