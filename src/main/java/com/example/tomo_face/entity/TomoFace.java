package com.example.tomo_face.entity;

import com.example.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TomoFace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long memberId;
    String title;
    String contents;
    String file;

    @Transient
    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
}
