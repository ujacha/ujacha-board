package net.ujacha.board.api.controller;

import net.ujacha.board.api.common.Const;
import net.ujacha.board.api.common.TestProfile;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.entity.MemberRole;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import net.ujacha.board.api.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(TestProfile.TEST)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeTestClass
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("아티클 생성 폼")
    public void testCreateArticleForm() throws Exception {
        // Given
        Board board = boardRepository.save(Board.builder().displayOrder(0).title("TEST BOARD").build());

        // When

        // not login
        mockMvc.perform(get("/createArticle").param("boardId", board.getId().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // login
        mockMvc.perform(get("/createArticle")
                .sessionAttr(Const.UJACHA_BOARD_LOGIN, 999L)
                .param("boardId", board.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk());


        // login & wrong board id
        mockMvc.perform(get("/createArticle")
                .sessionAttr(Const.UJACHA_BOARD_LOGIN, 999L)
                .param("boardId", "999"))
                .andDo(print())
                .andExpect(status().isNotFound());

        // Then

    }

    @Test
    @DisplayName("아티클 생성 요청")
    public void testCreateArticle() throws Exception {

        Member writer = memberRepository.save(Member.createMember("email@xxx.com", "password", "Example", MemberRole.USER));

        Board board = boardRepository.save(Board.builder().displayOrder(0).title("TEST BOARD").build());
        Category category = categoryRepository.save(Category.builder().board(board).name("CATEGORY").displayOrder(0).build());

        String boardId = board.getId().toString();
        String categoryId = category.getId().toString();
        String title = "Title";
        String text = "Sample Text";

        // 로그인
        mockMvc.perform(post("/createArticle")
                .sessionAttr(Const.UJACHA_BOARD_LOGIN, writer.getId())
                .param("boardId", boardId)
                .param("categoryId", categoryId)
                .param("title", title)
                .param("text", text)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/article?id=*"));

        // 비로그인
        mockMvc.perform(post("/createArticle")
//                .sessionAttr(Const.UJACHA_BOARD_LOGIN, writer.getId())
                .param("boardId", boardId)
                .param("categoryId", categoryId)
                .param("title", title)
                .param("text", text)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));


        // 필수 없음 -> bad request
        mockMvc.perform(post("/createArticle")
                .sessionAttr(Const.UJACHA_BOARD_LOGIN, writer.getId())
//                        .param("boardId", boardId)
                        .param("categoryId", categoryId)
                        .param("title", title)
                        .param("text", text)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

        // 필수 없음 -> bad request
        mockMvc.perform(post("/createArticle")
                        .sessionAttr(Const.UJACHA_BOARD_LOGIN, writer.getId())
                        .param("boardId", boardId)
                        .param("categoryId", categoryId)
                        .param("title", "")
                        .param("text", text)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

}