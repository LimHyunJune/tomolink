package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping
    public String index()
    {
        return "index";
    }

    @GetMapping("friends")
    public String friendsForm()
    {
        return "friends";
    }

    @GetMapping("add")
    public String addForm()
    {
        return "add";
    }

    @PostMapping("add")
    public String addPost() { return "redirect:/friends";}

    @GetMapping("signin")
    public String signInForm()
    {
        return "signin";
    }

    @GetMapping("signup")
    public String signUpForm()
    {
        return "signup";
    }

    @PostMapping("signup")
    public String signUp()
    {
        return "redirect:/signin";
    }
}
