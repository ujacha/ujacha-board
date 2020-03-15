package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)

public class Article extends CommonEntity {
    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @NonNull
    private Board board;

    @NonNull
    private String title;

    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    @NonNull
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

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

