package hello.spring_start.service;

import hello.spring_start.domain.Member;
import hello.spring_start.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {

    // DI 방식
    // 직접 생성하지 않고 생성자를 통해 외부에서 주입받기
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    // 각 테스트 실행 전에 호출된다
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 테스트 코드는 한글로 작성해도됨
    @Test
    @Commit
    // 테스트 코드에서 db를 조작했을 때, 테스트가 끝나도 그 데이터를 db에 영구적으로 반영하는 역할
    void 회원가입() {
        // given 뭔가가 주어졌는데
        Member member = new Member();
        member.setName("hello");

        // when 이걸 실행했을때
        Long saveId = memberService.join(member);

        // then 결과가 이게 나와야해
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // assertThrows: 두번째 인자의 코드를 실행했을때, 첫 번째 인자로 넣은 예외 타입이 발생해야만 테스트가 성공함
        // 자바에서는 모든 예외가 클래스 형태로 있는데 그 자체를 객체처럼 참조하고 싶을 때 .class를 붙인다

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}