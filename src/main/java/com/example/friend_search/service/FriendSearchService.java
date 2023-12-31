package com.example.friend_search.service;

import com.example.friend_search.entity.FriendSearch;
import com.example.friend_search.repository.FriendSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FriendSearchService {

    private final FriendSearchRepository friendSearchRepository;

    @Autowired
    FriendSearchService(FriendSearchRepository friendSearchRepository)
    {
        this.friendSearchRepository = friendSearchRepository;
    }

    @Transactional
    public FriendSearch save(FriendSearch friendSearch)
    {
        return friendSearchRepository.save(friendSearch);
    }

    public List<FriendSearch> findAll()
    {
        return friendSearchRepository.findAll();
    }

    public Optional<FriendSearch> findById(Long id) {
        return friendSearchRepository.findById(id);
    }

    public List<FriendSearch> findByMemberId(Long id) {
        return friendSearchRepository.findByMemberId(id);
    }
}
