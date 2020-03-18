package net.ujacha.board.api.repository;

import net.ujacha.board.api.common.TestProfile;
import net.ujacha.board.api.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testPage(){
        // Given
        Board board = boardRepository.save(Board.builder().title("B").displayOrder(0).build());
        Category category = categoryRepository.save(Category.builder().board(board).name("C").displayOrder(0).build());
        Member member = memberRepository.save(Member.createMember("e@m.m", "pass", "MEMBER", MemberRole.USER));

        articleRepository.save(Article.builder().board(board).category(category).writer(member).title("TITLE").text("TTTT").build());

        final Board findBoard = boardRepository.findTop1ByOrderByDisplayOrder();
        System.out.println("board = " + findBoard);
        final Category findCategory = categoryRepository.findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(findBoard).get(0);


        // When
        final Page<Article> page = articleRepository.findArticles(findBoard, findCategory, PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt"));

        // Then

        assertThat(page.getTotalElements()).isEqualTo(1);


    }

}