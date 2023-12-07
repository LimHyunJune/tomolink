package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
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


}
