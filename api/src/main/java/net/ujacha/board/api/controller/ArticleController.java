package net.ujacha.board.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.board.api.common.Const;
import net.ujacha.board.api.dto.ArticleForm;
import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.exception.BadRequestException;
import net.ujacha.board.api.exception.ResourceNotFoundException;
import net.ujacha.board.api.repository.ArticleRepository;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/article")
    public String article(@RequestParam(name = "id") long id, Model model) {

        Article article = articleRepository.findById(id).orElse(null);


        if (article == null) {
            throw new ResourceNotFoundException();
        }


        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(article.getBoard());

        model.addAttribute("article", article);
        model.addAttribute("categories", categories);

        return "article";

    }

    @GetMapping("/createArticle")
    public String createArticleForm(
            @RequestParam("boardId") long boardId,
            Model model,
            HttpSession session) {

        // check login session
        if (session.getAttribute(Const.UJACHA_BOARD_LOGIN) == null) {
            return "redirect:/login";
        }


        final Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("Not Found Board."));
        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(board);
        final ArticleForm form = new ArticleForm();

        model.addAttribute("board", board);
        model.addAttribute("categories", categories);
        model.addAttribute("form", form);

        return "articleForm";
    }

    @PostMapping("/createArticle")
    public String createArticle(@ModelAttribute @Valid ArticleForm form, Errors errors, RedirectAttributes redirectAttributes, HttpSession session) {



        // check login session
        if (session.getAttribute(Const.UJACHA_BOARD_LOGIN) == null) {
            return "redirect:/login";
        }

        // check validation errors
        if(errors.hasErrors()){
            // TODO 에러 처리
            throw new BadRequestException(errors.toString());
        }

        // get writer id from session
        final Long writerId = (Long) session.getAttribute(Const.UJACHA_BOARD_LOGIN);
        Board board = boardRepository.findById(form.getBoardId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Board"));

        // TODO Category 지정 안했을때
        // TODO Category 정책 정리
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Category."));
        Member writer = memberRepository.findById(writerId).orElseThrow(() -> new ResourceNotFoundException("Not Found Member."));
        Article newArticle = articleRepository.save(Article.builder()
                .board(board)
                .category(category)
                .writer(writer)
                .title(form.getTitle())
                .text(form.getText())
                .build());
        redirectAttributes.addAttribute("id", newArticle.getId());

        return "redirect:/article";
    }

}
