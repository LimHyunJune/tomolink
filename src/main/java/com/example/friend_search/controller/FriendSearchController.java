package com.example.friend_search.controller;

import com.example.friend_search.dto.FriendSearchPostForm;
import com.example.friend_search.entity.FriendSearch;
import com.example.friend_search.service.FriendSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/")
public class FriendSearchController {

    private final FriendSearchService friendSearchService;

    @Autowired
    public FriendSearchController(FriendSearchService friendSearchService)
    {
        this.friendSearchService = friendSearchService;
    }

    @ModelAttribute("tags")
    public Map<String, String> tags()
    {
        Map<String, String> tags = new LinkedHashMap<>();
        tags.put("TRIP","여행");
        tags.put("STUDY ABROAD","유학");
        tags.put("WORKING HOLIDAY","워홀");
        tags.put("LANGUAGE EXCHANGE","언어교환");
        tags.put("IDOL","아이돌");
        return tags;
    }

    @GetMapping("friend-search-board")
    public String friendSearchBoard(Model model) throws JsonProcessingException {

        List<FriendSearch> friendSearchPosts = friendSearchService.findAll();
        model.addAttribute("friendSearchPosts",friendSearchPosts);
        return "friend-search-board";
    }

    @GetMapping("friend-search-post")
    public String friendSearchPostForm(Model model)
    {
        return "friend-search-post";
    }

    @GetMapping("friend-search-post/{id}")
    @ResponseBody
    public String friendSearchPostFormByID(@PathVariable(value = "id") String id, Model model)
    {
        log.info("ID : {}",id);
        return id;
    }

    @PostMapping("friend-search-post")
    public String friendSearchPost(@ModelAttribute FriendSearchPostForm friendSearchPostForm, BindingResult bindingResult, Model model) {
        log.info("TITLE {}",friendSearchPostForm.getTitle());
        try
        {
            FriendSearch friendSearch = FriendSearch.builder()
                    .title(friendSearchPostForm.getTitle())
                    .contents(friendSearchPostForm.getContents())
                    .tag(friendSearchPostForm.getTag()).build();
            friendSearchService.save(friendSearch);
        }
        catch (Exception e)
        {
            log.info("ERROR {}",e.getMessage());
        }
        return "redirect:/friend-search-board";
    }
}
