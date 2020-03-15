package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString(of = {"id", "title", "displayOrder"})
public class Board extends CommonEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String title;

//    @OneToMany(mappedBy = "board")
//    private List<Category> categories = new ArrayList<>();

    private int displayOrder;

    @Builder
    public Board(String title, int displayOrder) {
        this.title = title;
        this.displayOrder = displayOrder;
    }
}
