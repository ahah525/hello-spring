package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // domain(lcoalhost8080)일 때 호출
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
