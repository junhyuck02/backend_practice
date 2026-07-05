package hello.spring_start.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
// 클래스를 스프링 빈으로 등록하기 위해 사용하는 가장 기본적인 어노테이션
// 자식들로 @Controller,@Service,@Repository 등이 있음
@Aspect
// 이 클래스가 AOP 기능을 수행함을 선언
public class TimeTraceAop {
    @Around("execution(* hello.spring_start..*(..))")
    // 어떤 메소드를 대상으로 할 것인가에 대한 경로 설정
    // execution: 메소드 실행 지점을 찾겠다
    // 첫 번째 *: 모든 반환 타입을 허용
    // hello.spring_start 패키지와 그 하위 패키지까지 모두 포함, ..은 하위 패키지까지 뒤지겠다는거임
    // *(..), *는 메소드 이름이고 (..)는 매개변수의 모든 경우를 허용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 무엇을 반환할지 모르니 Object를 반환, throws Throwable: 에러뜨면 난 못하니 위한테 짬때림
        // ProceedingJoinPoint: 내가 지금 가로채고 있는(감시하고 있는) 실제 메소드 그 자체를 의미하는 객체
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        // 어떤 메소드가 호출되었는지 로그를 남긴다
        // 로그를 남긴다: 프로그램이 돌아가는 과정에서 일어나는 일을 기록장에 적어둔다
        try {
            return joinPoint.proceed();
            // 이걸 해야 실제로 실행하려던 타겟 메소드가 실행된다
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
