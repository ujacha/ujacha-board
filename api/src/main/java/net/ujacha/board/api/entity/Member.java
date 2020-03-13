package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Member extends CommonEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    public Member(String name) {
        this(name, MemberRole.USER);
    }

    @Builder
    public Member(String name, MemberRole memberRole) {
        CommonEntity.initEntity(this);
        this.name = name;
        this.memberRole = memberRole;
    }
}
