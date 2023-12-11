package com.example.friend_search.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FriendSearchComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String contents;
    Long memberId;
    Long postId;
    Boolean isSecret;

    @Transient
    String name;

    @Transient
    Boolean isVisible;
}
