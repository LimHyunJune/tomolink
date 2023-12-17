package com.example.tomo_face.controller;

import com.example.common.util.FileUploadUtil;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.tomo_face.dto.ProfileForm;
import com.example.tomo_face.entity.Profile;
import com.example.tomo_face.entity.TomoFace;
import com.example.tomo_face.service.ProfileService;
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

    TomoFaceController(FileUploadUtil fileUploadUtil,
                       ProfileService profileService,
                       MemberService memberService)
    {
        this.fileUploadUtil = fileUploadUtil;
        this.memberService = memberService;
        this.profileService = profileService;
    }

    @GetMapping("tomo-face-board")
    public String tomoFaceBoard(Model model) {
        List<Profile> profiles = profileService.findAll();
        profiles.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        model.addAttribute("profiles", profiles);
        return "tomo-face-board";
    }

    @GetMapping("{name}")
    public String profile(@PathVariable(name = "name") String name, Model model){
        Member member = memberService.findByName(name);
        model.addAttribute("member", member);

        Optional<Profile> profile = profileService.findByName(name);
        profile.ifPresent(p->{model.addAttribute("profile", p);});
        if(profile.isEmpty())
        {
            Profile emptyProfile = Profile.builder()
                    .tomoFace(false)
                    .file("")
                    .build();
            model.addAttribute("profile", emptyProfile);
        }
        return "profile";
    }

    @PostMapping("{name}")
    public String profileForm(@PathVariable(name = "name") String name, @ModelAttribute ProfileForm profileForm, Model model) throws IOException {
        log.info("ProfileForm {}", profileForm);
        Profile profile = Profile.builder()
                .file(fileUploadUtil.uploadFile(profileForm.getFile()))
                .introduce(profileForm.getIntroduce())
                .name(profileForm.getName())
                .tomoFace(profileForm.getTomoFace())
                .createdAt(LocalDateTime.now()).build();
        profileService.update(profile);
        memberService.updateName(name);
        return "redirect:" + URLEncoder.encode("/" + name, "UTF-8");
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource downloadImage(@PathVariable(name = "filename") String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileUploadUtil.getFullPath(filename));
    }
}
