package hgrcompany.hgrshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // hello url로 매핑해준다.
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // data라는 이름으로 hello!!를 view에 전달
        return "hello"; // resources.templates.hello.html을 리턴
    }
}
