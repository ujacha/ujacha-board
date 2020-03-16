package net.ujacha.board.api.repository;

import net.ujacha.board.api.common.TestProfile;
import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
class ArticleRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void testPage(){
        // Given
        final Board board = boardRepository.findTop1ByOrderByDisplayOrder();
        // When
        final Page<Article> page = articleRepository.findByBoardAndDeletedAtIsNull(board, PageRequest.of(1, 1, Sort.Direction.DESC, "createdAt"));

        // Then

    }

}