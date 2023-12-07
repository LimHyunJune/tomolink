package com.example.controller;

import com.example.dto.SignInForm;
import com.example.dto.SignUpForm;
import com.example.entity.Member;
import com.example.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {

    private final MemberService memberService;
    @Autowired
    public LoginController(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @GetMapping("signin")
    public String signInForm()
    {
        return "signin";
    }

    @PostMapping("signin")
    public String signIn(@ModelAttribute SignInForm form, BindingResult bindingResult)
    {
        Member member = memberService.findByEmail(form.getEmail());
        if(member == null || !member.getPassword().equals(form.getPassword()))
            return "redirect:/signup";
        log.info("signin success");
        return "redirect:/";
    }

    @GetMapping("signup")
    public String signUpForm(Model model)
    {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("signup")
    public String signUp(@Validated @ModelAttribute SignUpForm form, BindingResult bindingResult, Model model)
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
