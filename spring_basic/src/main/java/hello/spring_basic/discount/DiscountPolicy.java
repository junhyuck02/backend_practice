package hello.spring_basic.discount;

import hello.spring_basic.member.Member;

public interface DiscountPolicy {

    // return이 할인 대상 금액
    int discount (Member member, int price);
}
