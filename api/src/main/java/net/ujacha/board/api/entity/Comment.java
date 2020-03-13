package net.ujacha.board.api.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Comment extends CommonEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    public Comment(Member writer, String text) {
        CommonEntity.initEntity(this);
        this.writer = writer;
        this.text = text;
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void addTo(Article article) {
        this.article = article;
    }
}
