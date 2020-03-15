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

        Member userA = Member.createMember("user1@example.com", "pass", "user1", MemberRole.USER);
        Member userB = Member.createMember("user2@example.com", "pass", "user2", MemberRole.USER);
        em.persist(userA);
        em.persist(userB);

        Category category = Category.builder().board(board).name("Category1").displayOrder(0).build();
        em.persist(category);

        Article article = Article.builder().board(board).writer(userA).category(category).title("title").text("text").build();
        em.persist(article);

        // When
        Comment comment = Comment.builder().article(article).writer(userB).text("comment").build();
        em.persist(comment);

        // Then


    }

}