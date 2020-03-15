package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString(of = {"id", "title", "displayOrder"})
public class Board extends CommonEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @NonNull
    private String title;

//    @OneToMany(mappedBy = "board")
//    private List<Category> categories = new ArrayList<>();

    @NonNull
    private Integer displayOrder;

}
