package com.TeamProject.TeamProject.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    public final MemberRepository memberRepository;


    public Member createMember(String membername, String password, String email) {
        Member member = new Member();
        member.setMembername(membername);
        member.setEmail(email);
        member.setPassword(password);
        member.setCreateDate(LocalDateTime.now());
        this.memberRepository.save(member);
        return member;
    }

    public Member getMember(String membername) {
        Optional<Member> member = this.memberRepository.findBymembername(membername);
        return member.get();

//        if (member.isPresent()) {
//            return member.get();
//        } else {
//            throw new DataNotFoundException("회원이 없습니다.");
//        }
    }


}
