package net.ujacha.board.api.entity;

import net.ujacha.board.api.common.TestProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
class BoardTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void Board_생성() {
        // Given
        final String title = "테스트 보드";
        Board board = Board.builder().title(title).displayOrder(0).build();

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