package net.ujacha.board.api.entity;

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
        Board board = Board.builder().title("Board1").displayOrder(0).build();
        em.persist(board);

        Member userA = new Member("user1@example.com", "pass", "user1", MemberRole.USER);
        Member userB = new Member("user2@example.com", "pass", "user2", MemberRole.USER);
        em.persist(userA);
        em.persist(userB);

        Category category = Category.builder().name("Category1").build();
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