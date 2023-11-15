package com.TeamProject.TeamProject.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {

    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;

    public Member create(String membername, String password,String email) {
        Member member = new Member();
        member.setMembername(membername);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setCreateDate(LocalDateTime.now());
        this.memberRepository.save(member);
        return member;
    }



}
