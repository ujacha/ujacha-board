package net.ujacha.board.api.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Article extends CommonEntity{
    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String title;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public static  Article createArticle(Board board, Member writer, String title, String text, Category category){

        return Article.builder()
                .board(board)
                .writer(writer)
                .title(title)
                .text(text)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .category(category)
                .build();
    }

    public void updateText(String text) {
        this.text = text;
        this.setLastModifiedAt(LocalDateTime.now());
    }

}
