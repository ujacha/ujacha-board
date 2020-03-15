package net.ujacha.board.api.entity;

import lombok.*;
import net.ujacha.board.api.common.PasswordUtils;

import javax.persistence.*;

@Entity
@Builder(access = AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id", "email", "password", "salt", "displayName", "memberRole"})
public class Member extends CommonEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String salt;

    @NonNull
    private String displayName;

    @Enumerated(EnumType.STRING)
    @NonNull
    private MemberRole memberRole;

    public static Member createMember(String email, String password, String displayName, MemberRole memberRole) {
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);
        return Member.builder()
                .email(email)
                .salt(salt)
                .password(securePassword)
                .displayName(displayName)
                .memberRole(memberRole)
                .build();
    }

}

