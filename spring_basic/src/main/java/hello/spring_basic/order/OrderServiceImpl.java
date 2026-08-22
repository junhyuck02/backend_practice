package hello.spring_basic.order;

import hello.spring_basic.annotation.MainDiscountPolicy;
import hello.spring_basic.discount.DiscountPolicy;
import hello.spring_basic.member.Member;
import hello.spring_basic.member.MemberRepository;
// import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 주문 서비스 구현체
// @RequiredArgsConstructor
// final 붙은걸 가지고 생성자를 자동으로 만들어주는 롬복 애너테이션
public class OrderServiceImpl implements OrderService {

    // private final MemberRepository memberRepository = new
    // MemoryMemberRepository();
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        // public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy
        // ratediscountPolicy) {
        // ~ @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        // this.discountPolicy = ratediscountPolicy;
        // 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭
        this.discountPolicy = discountPolicy;
    }
    // spring은 동일 타입 빈이 여러 개일 때 찾는 순서: @Qualifier -> @Primary -> 필드나 파라미터 이름으로 매칭

    @Override
    // 회원 조회 + 할인 계산 + 주문 생성
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
