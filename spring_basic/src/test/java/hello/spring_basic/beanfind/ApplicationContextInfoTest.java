package hello.spring_basic.beanfind;

import hello.spring_basic.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 컨테이너에 등록된 모든 빈 조회
public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    // AppConfig 클래스를 설정 정보로 사용해서 스프링 컨테이너(ApplicationContext)를 생성
    // 이 시점에 AppConfig에 정의된 @Bean 메서드들이 모두 실행되고, 그 반환 객체들이 컨테이너에 빈으로 등록됨

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){ // 컨테이너에 등록된 모든 빈 출력
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // 컨테이너에 등록된 모든 빈의 이름을 배열로 가져옴, 여기엔 내가 등록한 빈뿐만 아니라 스프링이 내부적으로 쓰는 빈도 포함됨
        for (String beanDefinitionName : beanDefinitionNames) {
            // 빈 이름 하나씩 순회
            Object bean = ac.getBean(beanDefinitionName);
            // 빈 이름으로 실제 빈 객체를 컨테이너에서 조회(꺼내옴)
            System.out.println("name=" + beanDefinitionName + " object=" + bean);
            // 빈 이름과 객체(toString() 결과, 보통 클래스명@해시코드)를 출력
        }
//        ac.getBeanDefinitionNames(): 스프링에 등록된 모든 빈 이름을 조회한다.
//        ac.getBean(): 빈 이름으로 빈 객체(인스턴스)를 조회한다.
    }
    // 컨테이너 안에 어떤 빈들이 다 들어있는지(내 빈 + 스프링 내부 빈 포함) 전부 확인하는 테스트

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() { // 내가 등록한 빈만 출력
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            // 빈 이름에 해당하는 빈의 메타정보(BeanDefinition)를 조회, 빈 객체 자체가 아니라, 그 빈이 어떻게 등록됐는지에 대한 정보(스코프, Role 등)

            // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈, 사용자가 직접 정의(예: @Bean, @Component)한 빈
            // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈, 스프링이 내부 동작을 위해 자동으로 등록한 빈 → 여기선 걸러짐
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                // Role 값으로 이 빈이 "내가 애플리케이션 개발을 위해 직접 등록한 빈"인지 구분
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name=" + beanDefinitionName + " object=" + bean);
            }
        }
    }

    // ROLE 값: BeanDefinition이 갖고 있는 속성 중 하나로, 이 빈이 어떤 목적으로 등록됐는지를 스프링이 구분해두는 값, int 상수로 정의
    //    ROLE_APPLICATION	0	사용자가 직접 등록한 애플리케이션 핵심 빈
    //    ROLE_SUPPORT	1	특정 컴포넌트의 보조 역할(설정 클래스 내부에서 파생된 빈 등)
    //    ROLE_INFRASTRUCTURE	2	스프링 프레임워크가 내부적으로 사용하는 빈, 사용자와 직접 관련 없음
}
