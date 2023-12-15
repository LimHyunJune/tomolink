package com.example.tomo_face.controller;

import com.example.common.util.FileUploadUtil;
import com.example.friend_search.entity.FriendSearch;
import com.example.friend_search.entity.FriendSearchComments;
import com.example.member.controller.MemberController;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.tomo_face.dto.TomoFacePostForm;
import com.example.tomo_face.entity.TomoFace;
import com.example.tomo_face.service.TomoFaceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class TomoFaceController {
    private final FileUploadUtil fileUploadUtil;
    private final TomoFaceService tomoFaceService;
    private final MemberService memberService;

    TomoFaceController(FileUploadUtil fileUploadUtil,
                       TomoFaceService tomoFaceService,
                       MemberService memberService)
    {
        this.fileUploadUtil = fileUploadUtil;
        this.tomoFaceService = tomoFaceService;
        this.memberService = memberService;
    }

    @GetMapping("tomo-face-board")
    public String tomoFaceBoard(Model model) {
        List<TomoFace> tomoFaces = tomoFaceService.findAll();
        for(TomoFace face : tomoFaces)
        {
            Optional<Member> member = memberService.findById(face.getMemberId());
            member.ifPresent(m-> {
                face.setName(m.getName());
            });
        }
        tomoFaces.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        model.addAttribute("tomoFaces", tomoFaces);
        return "tomo-face-board";
    }

    @GetMapping("tomo-face-post/{id}")
    public String tomoFacePost(@PathVariable(value = "id") Long id, Model model,
                                   HttpServletRequest request,
                                   @RequestParam(required = false, name = "scroll") Float scroll)
    {
        Optional<TomoFace> tomoFace = tomoFaceService.findById(id);
        tomoFace.ifPresent(face -> model.addAttribute("tomoFace", face));

//        List<TomoFace> friendSearchComments = friendSearchCommentsService.findByPostId(request, id);
//        model.addAttribute("friendSearchComments",friendSearchComments);
        model.addAttribute("scroll", scroll);
        return "tomo-face-post";
    }

    @GetMapping("tomo-face-post-form")
    public String tomoFacePostForm(Model model) {
        return "tomo-face-post-form";
    }

    @PostMapping("tomo-face-post-form")
    public String tomoFacePostForm(@ModelAttribute TomoFacePostForm tomoFacePostForm,
                                   BindingResult bindingResult, Model model, HttpServletRequest request)
    {
        try
        {
            HttpSession session = request.getSession(false);
            Member member = (Member) session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER);

            TomoFace tomoFace = TomoFace.builder()
                    .title(tomoFacePostForm.getTitle())
                    .contents(tomoFacePostForm.getContents())
                    .file(fileUploadUtil.uploadFile(tomoFacePostForm.getFile()))
                    .memberId(member.getId())
                    .createdAt(LocalDateTime.now()).build();
            tomoFaceService.save(tomoFace);
        }
        catch (Exception e)
        {
            log.info("ERROR {}",e.getMessage());
        }
        return "redirect:/tomo-face-board";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource downloadImage(@PathVariable(name = "filename") String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileUploadUtil.getFullPath(filename));
    }
}
