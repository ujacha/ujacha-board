package net.ujacha.board.api.service;

import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.common.PasswordUtils;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member joinMember(final Member member) {
        return memberRepository.save(member);
    }

    public Long getMemberId(final String email) {
        final Member findMember = memberRepository.findFirstByEmail(email);
        if (findMember != null) {
            return findMember.getId();
        }
        return null;
    }

    public boolean verifyPassword(final Long memberId, final String password) {
        final Optional<Member> member = memberRepository.findById(memberId);
        return member.map(m -> PasswordUtils.verifyUserPassword(password, m.getPassword(), m.getSalt())).orElse(false);
    }

}
