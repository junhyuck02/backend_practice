package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    // db의 Entity를 관리하는 관리자

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member) {
        em.persist(member);
        // 객체를 db에 저장한다
        return member;
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        // 기본키를 기준으로 db에서 객체를 찾아온다
        return Optional.ofNullable(member);
        // ofNullable: 이 값이 null일 수도 있을 때 사용하는 비어있는 상자
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // createQuery: JPQL이라는 언어로 작성한 주문서를 건네면서 조건대로 일을 하라고 명령
        // JPQL은 테이블이 아니라 엔티티(객체)를 대상으로 조회함
        // 쿼리: db에게 보내는 질문 또는 명령
        // Member.class: 조회된 결과를 Member 객체 타입으로 만들어라
        // getResultList(): 결과를 리스트 형태로 가져와라
    }

    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                // :name은 파라미터임, :name인 사람을 찾아달라는 뜻인데 무엇이 들어갈지는 아직 정해지지 않았음
                .setParameter("name", name)
                // :name이라는 빈칸에 실제 변수 name 값을 끼워 넣는다
                .getResultList();
        return result.stream().findAny();
    }

}
