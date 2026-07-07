package hello.spring_start.controller;

import org.springframework.ui.Model;
import hello.spring_start.domain.Member;
import hello.spring_start.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
// 이 어노테이션이 붙어 있어야 Spring이 이 클래스를 찾아서 요청을 처리하는 컨트롤러로 인식함
public class MemberController {

    private final MemberService memberService;

    @Autowired
    // 이 객체에 필요한 다른 객체를 spring 컨테이너에서 찾아와서 자동으로 연결해줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    // form 태그는 method 기본값이 GET임
    // POST 메소드는 주로 데이터를 생성하거나 변경하는 등 서버 상태를 변화시키는 요청에 사용된다. GET은 주로 정보 조회에 쓰인다
    // @GetMapping: 서버에서 데이터를 가져와 보여주기만 할 때 (조회)
    // @PostMapping: 서버에 데이터를 전송해서 저장하거나 변경할 때 (쓰기/수정) -> 사용자로부터 데이터를 입력받아 서버로 보낼 때 사용

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);
        return "members/memberList";
    }
}
