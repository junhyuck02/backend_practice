package selfStudy.inflearn_spring_start.repository;

import selfStudy.inflearn_spring_start.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 회원이 저장소에 저장됨
    Member save(Member member);

    // 저장소에서 찾아오기
    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    // 지금까지 저장된 모든 회원 리스트를 반환
    List<Member> findAll();
}
