package com.example.tomo_face.controller;

import com.example.common.util.FileUploadUtil;
import com.example.friend_search.service.FriendSearchCommentsService;
import com.example.friend_search.service.FriendSearchService;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.tomo_face.dto.ProfileForm;
import com.example.tomo_face.entity.Profile;
import com.example.tomo_face.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class TomoFaceController {
    private final FileUploadUtil fileUploadUtil;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final FriendSearchService friendSearchService;
    private final FriendSearchCommentsService friendSearchCommentsService;

    TomoFaceController(FileUploadUtil fileUploadUtil,
                       ProfileService profileService,
                       MemberService memberService,
                       FriendSearchService friendSearchService,
                       FriendSearchCommentsService friendSearchCommentsService)
    {
        this.fileUploadUtil = fileUploadUtil;
        this.memberService = memberService;
        this.profileService = profileService;
        this.friendSearchService = friendSearchService;
        this.friendSearchCommentsService = friendSearchCommentsService;
    }

    @GetMapping("tomo-face-board")
    public String tomoFaceBoard(Model model) {
        List<Profile> profiles = profileService.findTomoFaceProfiles();
        profiles.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        model.addAttribute("profiles", profiles);
        return "tomo-face-board";
    }

    @GetMapping("{id}")
    public String profile(@PathVariable(name = "id") Long id, Model model){
        Optional<Member> member = memberService.findById(id);
        member.ifPresent(m->{model.addAttribute("member", m);});

        Optional<Profile> profile = profileService.findByMemberId(id);
        profile.ifPresent(p->{model.addAttribute("profile", p);});
        if(profile.isEmpty())
        {
            Profile emptyProfile = Profile.builder()
                    .tomoFace(false)
                    .file("")
                    .build();
            model.addAttribute("profile", emptyProfile);
        }

        model.addAttribute("friendSearches", friendSearchService.findByMemberId(id));
        model.addAttribute("friendSearchComments", friendSearchCommentsService.findByMemberId(id));
        return "profile";
    }

    @PostMapping("{id}")
    public String profileForm(@PathVariable(name = "id") Long id
            , @ModelAttribute ProfileForm profileForm
            , @RequestParam(name = "name") String name
            , Model model
            , HttpServletRequest request) throws IOException {
        Optional<Profile> profile = profileService.findByMemberId(id);
        profile.ifPresent(p->{
            p.setTomoFace(profileForm.getTomoFace());
            p.setIntroduce(profileForm.getIntroduce());
            p.setCreatedAt(LocalDateTime.now());
            if(!profileForm.getFile().isEmpty()) {
                try {
                    p.setFile(fileUploadUtil.uploadFile(profileForm.getFile()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            profileService.update(p);
        });

        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute("member");
        if(profile.isEmpty())
        {
            Profile emptyProfile = Profile.builder()
                    .introduce(profileForm.getIntroduce())
                    .memberId(sessionMember.getId())
                    .tomoFace(profileForm.getTomoFace())
                    .createdAt(LocalDateTime.now()).build();
            if(profileForm.getFile() != null) {
                try {
                    emptyProfile.setFile(fileUploadUtil.uploadFile(profileForm.getFile()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            profileService.save(emptyProfile);
        }

        Optional<Member> member = memberService.findById(sessionMember.getId());
        member.ifPresent(m -> {
            m.setName(name);
            memberService.update(m);});
        return "redirect:/" + id;
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource downloadImage(@PathVariable(name = "filename") String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileUploadUtil.getFullPath(filename));
    }
}
