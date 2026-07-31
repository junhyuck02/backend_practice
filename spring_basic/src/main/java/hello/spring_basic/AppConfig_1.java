// 스프링을 사용하지 않은 순수 자바코드
// package hello.spring_basic;
//
//import hello.spring_basic.discount.DiscountPolicy;
//import hello.spring_basic.discount.FixDiscountPolicy;
//import hello.spring_basic.discount.RateDiscountPolicy;
//import hello.spring_basic.member.MemberRepository;
//import hello.spring_basic.member.MemberService;
//import hello.spring_basic.member.MemberServiceImpl;
//import hello.spring_basic.member.MemoryMemberRepository;
//import hello.spring_basic.order.OrderService;
//import hello.spring_basic.order.OrderServiceImpl;
//
//public class AppConfig_1 {
//
//    public MemberService memberService(){
//        return new MemberServiceImpl(memberRepository());
//    }
//
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
//
//    public OrderService orderService(){
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
//    }
//
//    public DiscountPolicy discountPolicy(){
//        // return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
//    }
//}
