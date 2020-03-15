package net.ujacha.board.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import net.ujacha.board.api.exception.ResourceNotFoundException;
import net.ujacha.board.api.repository.ArticleRepository;
import net.ujacha.board.api.repository.BoardRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model) {
        log.info("INDEX");

        final List<Board> boards = boardRepository.findAllByDeletedAtIsNullOrderByDisplayOrderAsc();
        model.addAttribute("boards", boards);

        final Board board = boardRepository.findTop1ByOrderByDisplayOrder();
        model.addAttribute("board", board);

        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByNameAsc(board);
        model.addAttribute("categories", categories);

        final Page<Article> page = articleRepository.findAll(PageRequest.of(0, 6, Sort.by("createdAt").descending()));
//        final List<Article> articles = articleRepository.findTop5ByBoardAndDeletedAtNullOrderByCreatedAtDesc(board);
        model.addAttribute("page", page);

        return "board";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") long id, Model model) {

        Article article = articleRepository.findById(id).orElse(null);


        if(article == null){
            throw new ResourceNotFoundException();
        }


        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByNameAsc(article.getBoard());

        model.addAttribute("article", article);
        model.addAttribute("categories", categories);

        return "article";
    }
}
