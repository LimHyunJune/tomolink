package com.example.friend_search.controller;

import com.example.friend_search.dto.FriendSearchCommentsForm;
import com.example.friend_search.dto.FriendSearchPostForm;
import com.example.friend_search.entity.FriendSearch;
import com.example.friend_search.entity.FriendSearchComments;
import com.example.friend_search.service.FriendSearchCommentsService;
import com.example.friend_search.service.FriendSearchService;
import com.example.member.controller.MemberController;
import com.example.member.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/")
public class FriendSearchController {

    private final FriendSearchService friendSearchService;
    private final FriendSearchCommentsService friendSearchCommentsService;

    @Autowired
    public FriendSearchController(FriendSearchService friendSearchService, FriendSearchCommentsService friendSearchCommentsService)
    {
        this.friendSearchService = friendSearchService;
        this.friendSearchCommentsService = friendSearchCommentsService;
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
    public String friendSearchBoard(Model model) {

        List<FriendSearch> friendSearchPosts = friendSearchService.findAll();
        model.addAttribute("friendSearchPosts",friendSearchPosts);
        return "friend-search-board";
    }

    @GetMapping("friend-search-post-form")
    public String friendSearchPostForm(Model model)
    {
        return "friend-search-post-form";
    }

    @PostMapping("friend-search-post-form")
    public String friendSearchPostForm(@ModelAttribute FriendSearchPostForm friendSearchPostForm
            , BindingResult bindingResult, Model model, HttpServletRequest request) {
        try
        {
            HttpSession session = request.getSession(false);
            Member member = (Member) session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER);

            FriendSearch friendSearch = FriendSearch.builder()
                    .title(friendSearchPostForm.getTitle())
                    .contents(friendSearchPostForm.getContents())
                    .tag(friendSearchPostForm.getTag())
                    .createdAt(LocalDateTime.now())
                    .memberId(member.getId()).build();
            friendSearchService.save(friendSearch);
        }
        catch (Exception e)
        {
            log.info("ERROR {}",e.getMessage());
        }
        return "redirect:/friend-search-board";
    }

    @GetMapping("friend-search-post/{id}")
    public String friendSearchPost(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request)
    {
        Optional<FriendSearch> friendSearch = friendSearchService.findById(id);
        friendSearch.ifPresent(search -> model.addAttribute("friendSearch", search));

        List<FriendSearchComments> friendSearchComments = friendSearchCommentsService.findByPostId(request, id);
        model.addAttribute("friendSearchComments",friendSearchComments);
        return "friend-search-post";
    }

    @PostMapping("friend-search-comments")
    public String friendSearchPostComments(@RequestParam(name = "isSecret") Boolean isSecret
            ,@ModelAttribute FriendSearchCommentsForm friendSearchCommentsForm
            ,BindingResult bindingResult
            ,HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER);

        FriendSearchComments friendSearchComments = FriendSearchComments.builder()
                .postId(friendSearchCommentsForm.getPostId())
                .memberId(member.getId())
                .contents(friendSearchCommentsForm.getContents())
                .isSecret(isSecret).build();
        friendSearchCommentsService.save(friendSearchComments);
        return "redirect:/friend-search-post/" + friendSearchComments.getPostId();
    }
}
