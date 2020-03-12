package net.ujacha.board.api.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BoardTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void Board_생성() {
        // Given
        final String title = "테스트 보드";
        Board board = new Board(title);

        // When
        em.persist(board);

        em.flush();
        em.clear();

        // Then
        Board findBoard = em.find(Board.class, board.getId());

        assertThat(findBoard.getId()).isEqualTo(board.getId());
        assertThat(findBoard.getTitle()).isEqualTo(title);

    }

}