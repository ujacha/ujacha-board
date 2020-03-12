package net.ujacha.board.api.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Category extends CommonEntity{
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;

    public Category(String name) {
        CommonEntity.initEntity(this);
        this.name = name;
    }
}
