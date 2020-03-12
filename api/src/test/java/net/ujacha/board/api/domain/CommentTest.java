package net.ujacha.board.api.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class CommentTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void Comment_저장() {
        // Given
        Board board = new Board("Board1");
        em.persist(board);

        Member userA = new Member("UserA");
        Member userB = new Member("UserB");
        em.persist(userA);
        em.persist(userB);

        Category category = new Category("Category1");
        em.persist(category);

        Article article = TestHelper.createArticle(board, userA, category, "title", "text");
        em.persist(article);

        // When
        Comment comment = new Comment(userB, "comment");
        em.persist(comment);

        article.addComment(comment);

        // Then


    }

}