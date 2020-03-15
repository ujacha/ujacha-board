package net.ujacha.board.api.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ArticleTest {

    @Autowired
    EntityManager em;

    @Test
    public void Article_저장() {
        // Given
        Member member = Member.createMember("email@example.com", "pass", "devin", MemberRole.USER);

        em.persist(member);

        Board board = Board.builder().title("Test Board").displayOrder(0).build();

        em.persist(board);

        // When
        for (int i = 0; i < 10; i++) {
            em.persist(Article.builder()
                    .board(board).writer(member).title("제목_" + i).text("내용 " + i).category(null).build());
        }
        final List<Article> result = em.createQuery(
                "select a" +
                        " from Article a" +
                        " join fetch a.writer" +
                        " join fetch a.board" +
                        " join a.board b" +
                        " where b = :board",
                Article.class)
                .setParameter("board", board)
                .getResultList();

        // Then
        assertThat(result.size()).isEqualTo(10);


    }

}