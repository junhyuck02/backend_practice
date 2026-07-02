package hello.spring_start.controller;

import hello.spring_start.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
// 이 어노테이션이 붙어 있어야 Spring이 이 클래스를 찾아서 요청을 처리하는 컨트롤러로 인식함
public class MemberController {

    private final MemberService memberService;

    @Autowired
    // 이 객체에 필요한 다른 객체를 spring 컨테이너에서 찾아와서 자동으로 연결해줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
