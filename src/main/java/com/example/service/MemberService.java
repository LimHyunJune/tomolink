package com.example.service;

import com.example.entity.Member;
import com.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member)
    {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email)
    {
        return memberRepository.findByEmail(email);
    }
}
