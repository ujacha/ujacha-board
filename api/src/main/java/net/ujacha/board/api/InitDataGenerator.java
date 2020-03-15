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
    public void run(ApplicationArguments args) {

        // admin user
        generateAdmin();

        // base data
        generateBaseData();

        // sample data
        generateSampleData();

    }


    private void generateAdmin() {
        memberRepository.save(Member.createMember("admin@ujacha.net", "admin1234", "ADMIN", MemberRole.ADMIN));
    }

    private void generateBaseData() {
        //Default Board
        Board board = Board.builder().title(DEFAULT_BOARD_TITLE).displayOrder(0).build();
        boardRepository.save(board);

        // Category
        for (int i = 0; i <DEFAULT_CATEGORIES.length ; i++) {
            categoryRepository.save(Category.builder().board(board).name(DEFAULT_CATEGORIES[i]).displayOrder(i).build());

        }

    }

    private void generateSampleData() {

        // 2nd board
        final Board newBoard = boardRepository.save(Board.builder().title("취미").displayOrder(1).build());

        for (int i = 0; i < 3; i++) {
            categoryRepository.save(Category.builder().board(newBoard).name("cate" + i).displayOrder(i).build());
        }

        Board board = boardRepository.findTop1ByOrderByDisplayOrder();
        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(board);
        categories.add(null);


        // member 10
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(memberRepository.save(Member.createMember(lorem.getEmail(), "pass1234", lorem.getName(), MemberRole.USER)));
        }

        Random random = new Random();

        // article 20
        for (int i = 0; i < 200; i++) {
            articleRepository.save(
                    Article.builder()
                            .board(board)
                            .writer(members.get(random.nextInt(members.size())))
                            .title(lorem.getTitle(3, 5))
                            .text(lorem.getHtmlParagraphs(2, 15))
                            .category(categories.get(random.nextInt(categories.size())))
                            .build()
            );
        }

    }
}
