package hello.spring_basic.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
// 메모리 회원 저장소 구현체
public class MemoryMemberRepository implements MemberRepository{

    // 데이터베이스가 아직 확정이 안되었다. 그래도 개발은 진행해야 하니 가장 단순한, 메모리 회원 저장소를 구현해서 우선 개발을 진행
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
