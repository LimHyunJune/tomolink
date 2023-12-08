package com.example.friend_search.controller;

import com.example.friend_search.dto.FriendSearchPostForm;
import com.example.friend_search.entity.FriendSearch;
import com.example.friend_search.service.FriendSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @ModelAttribute("postTypes")
    public Map<String, String> postTypes()
    {
        Map<String, String> postTypes = new LinkedHashMap<>();
        postTypes.put("TRIP","여행");
        postTypes.put("STUDY ABROAD","유학");
        postTypes.put("WORKING HOLIDAY","워홀");
        postTypes.put("LANGUAGE EXCHANGE","언어교환");
        postTypes.put("IDOL","아이돌");
        return postTypes;
    }

    @GetMapping("friend-search-board")
    public String friendSearchBoard()
    {
        return "friend-search-board";
    }

    @GetMapping("friend-search-post")
    public String friendSearchPostForm(Model model)
    {
        return "friend-search-post";
    }

    @PostMapping("friend-search-post")
    public String friendSearchPost(FriendSearchPostForm friendSearchPostForm, BindingResult bindingResult, Model model) {
        List<String> array = friendSearchPostForm.getPostTypes();
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            FriendSearch friendSearch = FriendSearch.builder()
                    .title(friendSearchPostForm.getTitle())
                    .contents(friendSearchPostForm.getContents())
                    .postTypes(objectMapper.writeValueAsString(array)).build();
            friendSearchService.save(friendSearch);
        }
        catch (IOException e)
        {
            throw new RuntimeException( e );
        }
        return "redirect:/friend-search-board";
    }
}
