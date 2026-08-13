package hello.spring_basic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.spring_basic", // 탐색할 패키지의 시작 위치를 지정
        // 이런거 안하고 디폴트 값은 해당 클래스의 패키지의 하위는 전부 다 뒤진다
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)
// @Component가 붙은 클래스를 찾아서 자동으로 스프링 빈으로 등록을 해준다
// 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록이 된다
// 그중에서 뺄거를 지정해주는게 excludeFilters, 자기자신은 탐색후보로 들어가지 않는다
public class AutoAppConfig {


}
