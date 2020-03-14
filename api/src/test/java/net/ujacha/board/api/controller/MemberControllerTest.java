package net.ujacha.board.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ujacha.board.api.common.Const;
import net.ujacha.board.api.dto.LoginForm;
import net.ujacha.board.api.entity.MemberRole;
import org.hamcrest.comparator.ComparatorMatcherBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공")
    public void testLogin() throws Exception {
        // Given
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("admin@ujacha.net");
        loginForm.setPassword("admin1234");
        // When
        mockMvc.perform(post("/login")
                .param("email", loginForm.getEmail())
                .param("password", loginForm.getPassword())
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttribute("UJACHA_BOARD_LOGIN", ComparatorMatcherBuilder.<Long>usingNaturalOrdering().greaterThan(0L)))
                .andExpect(request().sessionAttribute("UJACHA_BOARD_ROLE", MemberRole.ADMIN))
                .andExpect(request().sessionAttribute("UJACHA_BOARD_NAME", "ADMIN"));
        // Then

    }

    @Test
    @DisplayName("로그인 실패 - 이메일 없음")
    public void testLogin_fail_email() throws Exception {
        // Given
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("user@ujacha.net");
        loginForm.setPassword("admin1234");
        // When
        mockMvc.perform(post("/login")
                .param("email", loginForm.getEmail())
                .param("password", loginForm.getPassword())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().hasErrors())
//                .andExpect(model().attributeHasFieldErrorCode("form","eamil", "Not Found Member By Email."))
                .andExpect(request().sessionAttributeDoesNotExist(Const.UJACHA_BOARD_LOGIN, Const.UJACHA_BOARD_NAME, Const.UJACHA_BOARD_ROLE));
        // Then

    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    public void testLogin_fail_password() throws Exception {
        // Given
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("admin@ujacha.net");
        loginForm.setPassword("admin12345");
        // When
        mockMvc.perform(post("/login")
                .param("email", loginForm.getEmail())
                .param("password", loginForm.getPassword())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().hasErrors())
//                .andExpect(model().attributeHasFieldErrorCode("form","eamil", "Not Found Member By Email."))
                .andExpect(request().sessionAttributeDoesNotExist(Const.UJACHA_BOARD_LOGIN, Const.UJACHA_BOARD_NAME, Const.UJACHA_BOARD_ROLE));
        // Then

    }

}