package hello.spring_basic.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
// 이 애노테이션을 어디에 붙일 수 있는지 대상을 정한다 - 클래스, 인터페이스, Enum에만 이 애노테이션을 붙일 수 있다는 의미
@Retention(RetentionPolicy.RUNTIME)
// 이 애노테이션 정보가 언제까지 유지되는지(생명주기)를 지정 - 프로그램이 실행 중인 런타임 시점까지 애노테이션 정보가 남아있게 한다
@Documented
// 자바독(Javadoc) 문서를 생성할 때, 이 애노테이션 정보도 문서에 함께 표기되도록 설정
public @interface MyIncludeComponent {

}
