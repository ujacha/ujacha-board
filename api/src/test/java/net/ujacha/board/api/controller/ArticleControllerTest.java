package net.ujacha.board.api.controller;

import net.ujacha.board.api.common.Const;
import net.ujacha.board.api.common.TestProfile;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.repository.BoardRepository;
import org.aspectj.lang.annotation.Before;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(TestProfile.TEST)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoardRepository boardRepository;

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

}