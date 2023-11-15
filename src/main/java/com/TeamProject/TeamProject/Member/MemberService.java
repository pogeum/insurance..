package com.TeamProject.TeamProject.Member;

import com.TeamProject.TeamProject.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {

    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;

    public Member createMember(String memberId, String password, String email) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setEmail(email);
        member.setPassword(password);
        this.memberRepository.save(member);
        return member;
    }

    public Member getMember(String memberId) {
        Optional<Member> member = this.memberRepository.findByMemberId(memberId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("회원이 없습니다.");
        }
    }
}
