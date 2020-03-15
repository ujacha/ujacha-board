package net.ujacha.board.api.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Comment extends CommonEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @NonNull
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    @NonNull
    private Article article;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    @NonNull
    private Member writer;


    public void updateText(String text) {
        this.text = text;
    }

    public void addTo(Article article) {
        this.article = article;
    }
}
