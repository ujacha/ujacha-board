package net.ujacha.board.api.entity;

import lombok.*;
import net.ujacha.board.api.common.PasswordUtils;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "email", "password", "salt", "displayName", "memberRole"})
public class Member extends CommonEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String salt;

    private String displayName;


    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public Member(String email, String password, String displayName, MemberRole memberRole) {
        this.email = email;
        this.salt = PasswordUtils.getSalt(30);
        this.password = PasswordUtils.generateSecurePassword(password, this.salt);
        this.displayName = displayName;
        this.memberRole = memberRole;
    }

}
