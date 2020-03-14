package net.ujacha.board.api.service;

import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.entity.MemberRole;
import net.ujacha.board.api.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testJoinMember(){
        // Given
        final String email = "user@example.com";
        final String password = "password";
        final String displayName = "UserA";
        final MemberRole memberRole = MemberRole.USER;
        Member member = new Member(email, password, displayName, memberRole);

        // When
        memberService.joinMember(member);

        // Then
        final Member findMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(findMember).isNotNull();

        final Long memberId = memberService.getMemberId(email);
        assertThat(memberId).isEqualTo(member.getId());

        final boolean verifyPassword = memberService.verifyPassword(memberId, password);
        assertThat(verifyPassword).isEqualTo(true);

        final boolean verifyPasswordFail = memberService.verifyPassword(memberId, "xxxx");
        assertThat(verifyPasswordFail).isEqualTo(false);


    }

}