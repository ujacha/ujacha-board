package net.ujacha.board.api.domain;

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
        Member member = new Member("Devin");

        em.persist(member);

        Board board = new Board("Test Board");

        em.persist(board);

        // When
        for (int i = 0; i < 10; i++) {
            em.persist(new Article(board, member, "제목_" + i, "내용 " + i, null));
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