package net.ujacha.board.api;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.*;
import net.ujacha.board.api.repository.ArticleRepository;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Transactional
@RequiredArgsConstructor
public class InitDataGenerator implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

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

        Board board = boardRepository.findTop1ByOrderByDisplayOrder();
        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByNameAsc(board);


        // member 10
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(memberRepository.save(new Member(lorem.getEmail(), "pass1234", lorem.getName(), MemberRole.USER)));
        }

        Random random = new Random();

        // article 20
        for (int i = 0; i < 20; i++) {
            articleRepository.save(
                    new Article(
                            board,
                            members.get(random.nextInt(members.size())),
                            lorem.getTitle(3, 5),
                            lorem.getHtmlParagraphs(2, 15),
                            categories.get(random.nextInt(categories.size())))
            );
        }

    }
}
