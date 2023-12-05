package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping
    public String index()
    {
        return "index";
    }

    @GetMapping("friends")
    public String friends()
    {
        return "friends";
    }

    @GetMapping("add")
    public String addForm()
    {
        return "add";
    }

    @PostMapping("add")
    public String addPost()
    {
        // PRG Pattern
        return "redirect:/friends";
    }
}
