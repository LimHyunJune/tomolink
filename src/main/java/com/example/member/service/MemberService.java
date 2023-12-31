package com.example.member.service;

import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(Member member)
    {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email)
    {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional
    public void update(Member memberForm) {
        Optional<Member> member = findById(memberForm.getId());
        member.ifPresent(m->{m.setName(memberForm.getName());});
    }
}
