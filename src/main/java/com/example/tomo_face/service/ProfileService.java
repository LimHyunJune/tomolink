package com.example.tomo_face.service;

import com.example.tomo_face.entity.Profile;
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

    ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Transactional
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Profile> findByName(String name) {
        return profileRepository.findByName(name);
    }

    @Transactional
    public void update(Profile profile) {
        Optional<Profile> updatedProfile = profileRepository.findByName(profile.getName());
        updatedProfile.ifPresent(p->{
            log.info("profile present");
            p.setFile(profile.getFile());
            p.setName(profile.getName());
            p.setIntroduce(profile.getIntroduce());
            p.setCreatedAt(profile.getCreatedAt());
            p.setTomoFace(profile.getTomoFace());
        });
        if(updatedProfile.isEmpty())
        {
            log.info("profile empty");
            save(profile);
        }
    }
}
