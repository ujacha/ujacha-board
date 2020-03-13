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
public class IndexController {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model) {
        log.info("INDEX");

        final Board board = boardRepository.findTop1ByOrderByDisplayOrder();
        model.addAttribute("board", board);

        final List<Category> categories = board.getCategories();
        model.addAttribute("categories", categories);

        final Page<Article> page = articleRepository.findAll(PageRequest.of(0, 2, Sort.by("createdAt").descending()));
//        final List<Article> articles = articleRepository.findTop5ByBoardAndDeletedAtNullOrderByCreatedAtDesc(board);
        model.addAttribute("page", page);

        return "index";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") long id, Model model) {

        Article article = articleRepository.findById(id).orElse(null);
        if(article == null){
            throw new ResourceNotFoundException();
        }
        model.addAttribute("article", article);

        return "article";
    }
}
