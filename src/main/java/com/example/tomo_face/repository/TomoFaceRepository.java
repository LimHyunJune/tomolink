package com.example.tomo_face.repository;

import com.example.tomo_face.entity.TomoFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TomoFaceRepository extends JpaRepository<TomoFace,Long> {
}
