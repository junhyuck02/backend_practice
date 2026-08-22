package hello.spring_basic.discount;

import hello.spring_basic.annotation.MainDiscountPolicy;
import hello.spring_basic.member.Grade;
import hello.spring_basic.member.Member;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
// @Qualifier("mainDiscountPolicy") // 추가 구분자를 붙여주는 방법
// @Primary 우선순위가 최상위로 잡힌다
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
