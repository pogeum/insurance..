package com.TeamProject.TeamProject.Member;

import com.TeamProject.TeamProject.DataNotFoundException;
import com.TeamProject.TeamProject.Restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberService {

    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;

    public Member create(String memberId, String password, String nickname, String email) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickname(nickname);
        member.setEmail(email);

        this.memberRepository.save(member);
        return member;
    }
    //------------------------------------------------------------------------------------------------------------------
    public Member getMemberforreview(String memberId) {
        // 황선영이 추가. 회원정보없이 일단 리뷰작성되는지 확인하기 위해서 작성한 메서드. 추후 수정예정.
        Optional<Member> member = Optional.ofNullable(memberId)
                .flatMap(id -> this.memberRepository.findByMemberId(id));

        return member.orElseGet(() -> {
            Member unknownMember = new Member();
            unknownMember.setNickname("비회원" + UUID.randomUUID().toString() );
            return memberRepository.save(unknownMember);
        });
    }
    //------------------------------------------------------------------------------------------------------------------
    public Member getMember(String memberId) {
        Optional<Member> member = this.memberRepository.findByMemberId(memberId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("회원이 없습니다.");
        }
    }
}
