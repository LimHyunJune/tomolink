package com.example.friend_search.service;

import com.example.friend_search.entity.FriendSearchComments;
import com.example.friend_search.repository.FriendSearchCommentQueryRepository;
import com.example.friend_search.repository.FriendSearchCommentsRepository;
import com.example.friend_search.repository.FriendSearchRepository;
import com.example.member.controller.MemberController;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FriendSearchCommentsService {

    private final FriendSearchCommentsRepository friendSearchCommentsRepository;
    private final FriendSearchCommentQueryRepository friendSearchCommentQueryRepository;

    private final MemberService memberService;

    @Autowired
    FriendSearchCommentsService(FriendSearchCommentsRepository friendSearchCommentsRepository,
                                FriendSearchCommentQueryRepository friendSearchCommentQueryRepository,
                                MemberService memberService)
    {
        this.friendSearchCommentQueryRepository = friendSearchCommentQueryRepository;
        this.friendSearchCommentsRepository = friendSearchCommentsRepository;
        this.memberService = memberService;
    }

    @Transactional
    public FriendSearchComments save(FriendSearchComments friendSearchComments)
    {
        return friendSearchCommentsRepository.save(friendSearchComments);
    }

    public List<FriendSearchComments> findByPostId(HttpServletRequest request, Long postId) {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER);
        List<FriendSearchComments> friendSearchComments = friendSearchCommentQueryRepository.findByPostId(postId, sessionMember.getId());

        friendSearchComments.forEach(comment -> {
            memberService.findById(comment.getMemberId()).ifPresent(member-> comment.setName(member.getName()));
        });
        return friendSearchComments;
    }

    public List<FriendSearchComments> findByMemberId(Long id) {
        return friendSearchCommentsRepository.findByMemberId(id);
    }
}
