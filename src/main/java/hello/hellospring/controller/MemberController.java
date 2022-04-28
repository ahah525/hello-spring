package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // private final MemberService memberService = new MemberService();
    // 스프링 컨테이너에 등록을 해두고 가져다 쓰는 방식(같은 MemberService를 공유하도록)
    private final MemberService memberService;

    @Autowired
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
        member.setName(form.getName()); // form 에서 받은 name 넣어줌

        memberService.join(member); // 회원 가입
        //System.out.println("member = "+ member.getName());

        return "redirect:/";    // 홈 화면으로

    }

    @GetMapping("/members")
    public String list(Model model) {
        // 모든 회원 리스트로 반환
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
