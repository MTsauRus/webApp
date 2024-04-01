package hgrcompany.hgrshop.controller;

import hgrcompany.hgrshop.Service.MemberService;
import hgrcompany.hgrshop.domain.Address;
import hgrcompany.hgrshop.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/new") // 폼 화면을 여는 역할
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 폼을 등록하는 역할
    public String create(@Valid MemberForm form, BindingResult result) { // MemberForm에 있는 NotEmpty를 validation
        // BindingResult: 에러 발생 시 해당 에러를 저장함.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member); // persist

        return "redirect:/"; // 홈으로
    }

}
