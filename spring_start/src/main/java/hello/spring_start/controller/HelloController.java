package hello.spring_start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
// 이 어노테이션이 붙어 있어야 Spring이 이 클래스를 찾아서 요청을 처리하는 컨트롤러로 인식함
public class HelloController {

    @GetMapping("hello")
    // 웹 브라우저에서 /hello라는 URL로 HTTP GET 요청이 들어오면, 바로 아래에 있는 hello 메서드를 실행
    public String hello(Model model) {
        model.addAttribute("data", "hello spring!!!!");
        // addAttribute: 저장이 아니라 전달용임, 임시 메모리에 데이터를 담아두는 거지 db에 저장하는게 아님
        // 바인딩: 데이터를 뷰와 연결한다
        // 뷰에 전달하시 위해 model 객체에 데이터를 담는다, 모델에 데이터를 바인딩한다
        return "hello";
        // View Resolver에게 hello라는 이름의 화면을 찾아서 띄우라는 것
    }

    // MVC와 템플릿 엔진 예시
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // RequestParam - localhost:8080/hello-mvc?name=hi -> 여기서 hi를 꺼내서 name 변수에 저장
        // Model model: controller -> view로 데이터를 전달하는 객체, 직접 생성하는게 아니라 스프링이 자동으로 만들어서 인자로 넣어줌
        model.addAttribute("name", name);
        return "hello-template";
        // return하면 뷰 리졸버가 html 파일을 찾음
    }

    // API 예시
    @GetMapping("hello-string")
    @ResponseBody
    // ResponseBody: view를 거치지 않고 HTTP의 body에 내가 데이터를 직접 넣어주겠다 - viewResolver를 사용하지 않음
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
