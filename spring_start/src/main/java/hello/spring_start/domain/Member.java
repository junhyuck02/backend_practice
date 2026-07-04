package hello.spring_start.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
// 이 클래스는 나중에 db의 테이블로 만들어야겠다고 인식, 클래스 자체가 db와 1:1로 매핑되는 것임
public class Member {

    @Id
    // 해당 필드를 테이블의 기본키로 설정
    // 기본키(Primary Key, PK): 각 데이터를 구분할 수 있는 유일한 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GeneratedValue: 엔티티의 기본키를 자동으로 생성하겠다는 뜻
    // 어떻게? strategy = GenerationType.IDENTITY 이렇게 , strategy는 속성명이라 바꾸면 안됨
    // GenerationType enum에서 IDENTITY(DB의 auto-increment에 위임) 방식을 선택
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
