package net.ujacha.board.api;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.entity.MemberRole;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataGenerator implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    private static final String DEFAULT_BOARD_TITLE = "NEWS";

    private static final String[] DEFAULT_CATEGORIES = {
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

    private final Lorem lorem = LoremIpsum.getInstance();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // admin user
        generateAdmin();

        // base data
        generateBaseData();

        // sample data
        generateSampleData();

    }


    private void generateAdmin() {
        memberRepository.save(new Member("admin@ujacha.net", "admin1234", "ADMIN", MemberRole.ADMIN));
    }

    private void generateBaseData() {
        //Default Board
        Board board = Board.builder().title(DEFAULT_BOARD_TITLE).displayOrder(0).build();
        boardRepository.save(board);

        // Category
        for (String categoryName : DEFAULT_CATEGORIES) {
            categoryRepository.save(Category.builder().board(board).name(categoryName).build());
        }

    }

    private void generateSampleData() {
        //TODO
//        articleRepository.save(new Article(board, member, lorem.getTitle(3, 5), lorem.getHtmlParagraphs(2, 15), null));
    }
}
