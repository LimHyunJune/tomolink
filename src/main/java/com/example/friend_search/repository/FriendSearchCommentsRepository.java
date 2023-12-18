package com.example.friend_search.repository;

import com.example.friend_search.entity.FriendSearchComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendSearchCommentsRepository extends JpaRepository<FriendSearchComments, Long> {
    List<FriendSearchComments> findByPostId(Long postId);
    List<FriendSearchComments> findByMemberId(Long id);
}
