package com.example.friend_search.repository;

import com.example.friend_search.entity.FriendSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendSearchRepository extends JpaRepository<FriendSearch, Long> {
}
