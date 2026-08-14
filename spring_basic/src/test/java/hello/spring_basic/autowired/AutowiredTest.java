package hello.spring_basic.autowired;

import hello.spring_basic.member.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.de.siegmar.fastcsv.util.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {
    // 스프링 컨테이너에 등록되지 않은 대상(Member)을 의존관계 주입(@Autowired)할 때,
    // 옵션별로 스프링이 어떻게 동작하는지를 확인하는 테스트 코드

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // 주입할 스프링 빈이 없으면 수정자(Setter) 메서드 자체가 호출되지 않는다
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        // 주입할 대상이 없어도 메서드가 호출되며, null이 파라미터로 주입된다
        @Autowired(required = false)
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        // 주입할 대상이 없으면 Optional.empty가 주입된 상태로 메서드가 호출된다
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
