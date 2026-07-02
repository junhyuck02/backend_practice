package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
// 이 클래스를 객체로 만들어서 spring 컨테이너에 넣어둔다
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();

    // 회원 가입 시마다 0,1,2.. key갑을 생성해주는 애
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 결과 값이 null일 경우에 대비해 Optional로 감싸기
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 걸러진 회원 중 일치하는 하나를 반환한다
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 저장소 비우기
    public void clearStore() {
        store.clear();
    }

}
