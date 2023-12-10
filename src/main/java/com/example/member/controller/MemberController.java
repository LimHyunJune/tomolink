package com.example.member.controller;

import com.example.member.service.MemberService;
import com.example.member.dto.SignInForm;
import com.example.member.dto.SignUpForm;
import com.example.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
public class MemberController {
    public static class SessionConst {
        public static final String LOGIN_MEMBER = "member";
    }
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @GetMapping("signin")
    public String signInForm()
    {
        return "signin";
    }

    @PostMapping("signin")
    public String signIn(@ModelAttribute SignInForm form, BindingResult bindingResult,
                         HttpServletRequest request, @RequestParam(defaultValue = "/", name = "redirectURL") String redirectURL)
    {
        Member member = memberService.findByEmail(form.getEmail());
        log.info("MEMBER : {}",member);
        if(member == null || !member.getPassword().equals(form.getPassword()))
        {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "redirect:/signin";
        }

        log.info("signin success");
        // Session 저장
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:" + redirectURL;
    }

    @GetMapping("signup")
    public String signUpForm(Model model)
    {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("signup")
    public String signUp(@Validated @ModelAttribute SignUpForm form, BindingResult bindingResult,
                         Model model)
    {
        log.info("email = {}", form.getEmail());
        if(!form.getPassword().equals(form.getConfirmPassword()))
            bindingResult.addError(new FieldError("signUpForm","confirmPassword","패스워드가 일치하지 않습니다."));

        if(bindingResult.hasErrors())
        {
            log.info("errors={}", bindingResult);
            model.addAttribute("signUpForm",form);
            return "signup";
        }

        Member member = Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword()).build();
        memberService.save(member);
        return "redirect:/signin";
    }
}
