package net.ujacha.board.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.board.api.common.Const;
import net.ujacha.board.api.dto.JoinForm;
import net.ujacha.board.api.dto.LoginForm;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.entity.MemberRole;
import net.ujacha.board.api.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model) {

        JoinForm joinForm = new JoinForm();
        model.addAttribute("form", joinForm);
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute @Valid JoinForm form, Errors errors, Model model) {
        log.debug("JOIN : {}", form);

        if (errors.hasErrors()) {
            log.error("JOIN : {}", errors);
            throw new IllegalArgumentException();
        }

        Member member = Member.createMember(form.getEmail(), form.getPassword(), form.getDisplayName(), MemberRole.USER);
        memberService.joinMember(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginForm form = new LoginForm();
        model.addAttribute("form", form);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginForm form, Errors errors, Model model, HttpSession session) {

        // Save Login Session

        clearLoginSession(session);

        LoginForm newForm = new LoginForm();
        newForm.setEmail(form.getEmail());
        model.addAttribute("form", newForm);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException();
        }

        final Long memberId = memberService.getMemberId(form.getEmail());

        if (memberId == null) {
            log.debug("Not Found Member By Email.");
            errors.rejectValue("email", "notFoundMember", "Not Found Member By Email.");
            return "login";
        }

        final boolean verifyPassword = memberService.verifyPassword(memberId, form.getPassword());

        if (!verifyPassword) {
            log.debug("Wrong Password.");
            errors.rejectValue("password", "wrongPassword", "Wrong Password.");
            return "login";
        }

        Member loginMember = memberService.findMember(memberId);

        saveLoginSession(session, loginMember);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){

        clearLoginSession(session);

        return "redirect:/";

    }

    private void saveLoginSession(HttpSession session, Member loginMember) {
        session.setAttribute(Const.UJACHA_BOARD_LOGIN, loginMember.getId());
        session.setAttribute(Const.UJACHA_BOARD_ROLE, loginMember.getMemberRole());
        session.setAttribute(Const.UJACHA_BOARD_NAME, loginMember.getDisplayName());
    }

    private void clearLoginSession(HttpSession session) {
        session.removeAttribute(Const.UJACHA_BOARD_LOGIN);
        session.removeAttribute(Const.UJACHA_BOARD_ROLE);
        session.removeAttribute(Const.UJACHA_BOARD_NAME);
    }

}
