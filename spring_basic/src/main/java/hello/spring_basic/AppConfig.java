package hello.spring_basic;

import hello.spring_basic.discount.DiscountPolicy;
import hello.spring_basic.discount.RateDiscountPolicy;
import hello.spring_basic.member.MemberRepository;
import hello.spring_basic.member.MemberService;
import hello.spring_basic.member.MemberServiceImpl;
import hello.spring_basic.member.MemoryMemberRepository;
import hello.spring_basic.order.OrderService;
import hello.spring_basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// AppConfig 스프링 기반으로 변경
@Configuration // AppConfig에 설정을 구성한다는 뜻
public class AppConfig {

    // @Bean memberSevice -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository


    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }
}
