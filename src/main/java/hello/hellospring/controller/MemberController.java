package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    // private final MemberService memberService = new MemberService();
    // 스프링 컨테이너에 등록을 해두고 가져다 쓰는 방식(같은 MemberService를 공유하도록)
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
