package net.ujacha.board.api.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Member extends CommonEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}
