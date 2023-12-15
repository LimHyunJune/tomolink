package com.example.tomo_face.service;

import com.example.tomo_face.entity.TomoFace;
import com.example.tomo_face.repository.TomoFaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TomoFaceService {

    private final TomoFaceRepository tomoFaceRepository;

    TomoFaceService(TomoFaceRepository tomoFaceRepository)
    {
        this.tomoFaceRepository = tomoFaceRepository;
    }

    @Transactional
    public TomoFace save(TomoFace tomoface)
    {
        return tomoFaceRepository.save(tomoface);
    }

    public List<TomoFace> findAll()
    {
        return tomoFaceRepository.findAll();
    }

    public Optional<TomoFace> findById(Long id){ return tomoFaceRepository.findById(id); }
}
