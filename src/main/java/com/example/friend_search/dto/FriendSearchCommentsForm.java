package com.example.friend_search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendSearchCommentsForm {
    String contents;
    Long postId;
    Boolean isSecret;
}
