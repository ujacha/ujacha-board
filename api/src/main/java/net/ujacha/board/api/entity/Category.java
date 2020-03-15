package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Category extends CommonEntity{
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    private String name;

    @Builder
    public Category(Board board, String name) {
        this.board = board;
        this.name = name;

//        board.getCategories().add(this);
    }
}
