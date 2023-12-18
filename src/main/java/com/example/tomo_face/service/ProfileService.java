package com.example.tomo_face.service;

import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;
import com.example.tomo_face.entity.Profile;
import com.example.tomo_face.repository.ProfileQueryRepository;
import com.example.tomo_face.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileQueryRepository profileQueryRepository;

    private final MemberRepository memberRepository;

    ProfileService(ProfileRepository profileRepository,
                   ProfileQueryRepository profileQueryRepository,
                   MemberRepository memberRepository)
    {
        this.profileRepository = profileRepository;
        this.profileQueryRepository = profileQueryRepository;
        this.memberRepository = memberRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Transactional
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Transactional
    public void update(Profile profile) {
        Optional<Profile> updatedProfile = profileRepository.findByMemberId(profile.getMemberId());
        updatedProfile.ifPresent(p->{
            p.setFile(profile.getFile());
            p.setIntroduce(profile.getIntroduce());
            p.setCreatedAt(profile.getCreatedAt());
            p.setTomoFace(profile.getTomoFace());
            log.info("update profile : {}", p);
        });
    }

    public Optional<Profile> findByMemberId(Long memberId) {
        return profileRepository.findByMemberId(memberId);
    }

    public List<Profile> findTomoFaceProfiles() {
        List<Profile> profiles = profileQueryRepository.findTomoFaceProfiles();
        for(Profile profile : profiles)
        {
            Optional<Member> member = memberRepository.findById(profile.getMemberId());
            member.ifPresent(m ->{profile.setName(m.getName());});
        }
        return profiles;
    }
}
