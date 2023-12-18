package com.example.friend_search.repository;

import com.example.friend_search.entity.FriendSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendSearchRepository extends JpaRepository<FriendSearch, Long> {
    List<FriendSearch> findByMemberId(Long id);
}
