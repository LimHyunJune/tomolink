package com.example.friend_search.service;

import com.example.friend_search.entity.FriendSearchComments;
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
    private final MemberService memberService;

    @Autowired
    FriendSearchCommentsService(FriendSearchCommentsRepository friendSearchCommentsRepository,
                                MemberService memberService)
    {
        this.friendSearchCommentsRepository = friendSearchCommentsRepository;
        this.memberService = memberService;
    }

    @Transactional
    public FriendSearchComments save(FriendSearchComments friendSearchComments)
    {
        return friendSearchCommentsRepository.save(friendSearchComments);
    }

    public List<FriendSearchComments> findByPostId(HttpServletRequest request, Long postId)
    {
        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER);

        List<FriendSearchComments> friendSearchComments = friendSearchCommentsRepository.findByPostId(postId);
        for(FriendSearchComments friendSearchComment : friendSearchComments)
        {
            Optional<Member> member = memberService.findById(friendSearchComment.getMemberId());
            member.ifPresent(value -> friendSearchComment.setName(value.getName()));

            if(friendSearchComment.getIsSecret() == false ||
            (friendSearchComment.getIsSecret() == true && sessionMember.getId() == member.get().getId()))
            {
                friendSearchComment.setIsVisible(true);
            }
            else
            {
                friendSearchComment.setIsVisible(false);
            }
        }
        return friendSearchComments;
    }
}
