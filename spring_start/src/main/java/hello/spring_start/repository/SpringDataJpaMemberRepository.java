package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // JpaRepository: 스프링 데이터 JPA가 제공하는 인터페이스
    // 이를 상속받기만 하면, 데이터를 저장하고, 수정하고, 삭제하고, 조회하는 수십 가지의 복잡한 기능을 자동으로 구현해줌
    // 스프링 부트가 애플리케이션을 시작할 때, @Repository 어노테이션이 붙어 있거나 JpaRepository를 상속받은 인터페이스를 찾는다
    // 스프링 데이터 JPA가 프록시(대역: 임시객체)를 만들고 SpringDataJpaMemberRepository 를 스프링 빈으로 자동 등록해준다
    // 내가 Member라는 객체를 관리할 건데, 이 객체의 PK는 Long 타입이고 JpaRepository가 가진 기능들을 다 가져와서 MemberRepository에 넣어줘 라는 뜻

    @Override
    Optional<Member> findByName(String name);
}