package com.example.friend_search.dto;

import lombok.Data;

import java.util.List;

@Data
public class FriendSearchPostForm {

    String title;
    String contents;
    List<String> postTypes;

}

