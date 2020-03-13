package net.ujacha.board.api.service;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.*;
import net.ujacha.board.api.repository.ArticleRepository;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    private final Lorem lorem = LoremIpsum.getInstance();

    public static final String BOARD_TITLE = "NEWS";

    private static final String[] CATEGORY_NAMES = {
            "World",
            "U.S.",
            "Technology",
            "Design",
            "Culture",
            "Business",
            "Politics",
            "Opinion",
            "Science",
            "Health",
            "Style",
            "Travel"
    };

    @PostConstruct
    public void init() {

        Board board = Board.builder().title(BOARD_TITLE).displayOrder(0).build();

        boardRepository.save(board);

        for (String categoryName : CATEGORY_NAMES) {
            categoryRepository.save(Category.builder().board(board).name(categoryName).build());
        }

        Member member = Member.builder().name(lorem.getName()).memberRole(MemberRole.USER).build();
        memberRepository.save(member);

        for (int i = 0; i < 10; i++) {
            articleRepository.save(new Article(board, member, lorem.getTitle(3, 5), lorem.getHtmlParagraphs(2, 15), null));
        }
    }
}
