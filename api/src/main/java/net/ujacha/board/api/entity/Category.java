package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @NonNull
    private Board board;

    @NonNull
    private String name;

    @NonNull
    private Integer displayOrder;

//    public Category(Board board, String name) {
//        this.board = board;
//        this.name = name;

//        board.getCategories().add(this);
//    }
}
