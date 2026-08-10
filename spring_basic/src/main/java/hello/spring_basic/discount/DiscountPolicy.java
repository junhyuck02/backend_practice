package hello.spring_basic.discount;

import hello.spring_basic.member.Member;

// 할인 정책 인터페이스
public interface DiscountPolicy {

    // return이 할인 대상 금액
    int discount (Member member, int price);
}
