package hello.spring_start.repository;

import hello.spring_start.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
// import static 이렇게 하면 클래스 이름없이 메소드를 바로 사용할 수 있음


class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // Test코드에서는 함수가 각각 돌아가게끔 만들어야한다

    @AfterEach
    // 각 테스트가 끝날 때마다 이 함수가 실행되게끔 함
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    // 테스트 대상이라고 알려주는 것
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
        // assertThat은 검증 대상을 설정한다. isEqualTo는 값을 비교한다
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
