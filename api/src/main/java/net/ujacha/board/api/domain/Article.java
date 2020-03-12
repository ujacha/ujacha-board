package net.ujacha.board.api.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Article extends CommonEntity {
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

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    public Article(Board board, Member writer, String title, String text, Category category) {
        CommonEntity.initEntity(this);
        this.board = board;
        this.writer = writer;
        this.title = title;
        this.text = text;
        this.category = category;

    }

    public void updateText(String text) {
        this.text = text;
        this.setLastModifiedAt(LocalDateTime.now());
    }

    public Comment addComment(Comment comment) {
        comment.addTo(this);
        comments.add(comment);
        return comment;
    }
}
