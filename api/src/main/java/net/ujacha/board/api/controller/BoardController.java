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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @GetMapping("/board")
    public String board(
            @RequestParam(name = "id", required = true) long id,
            @RequestParam(name = "category", required = false, defaultValue = "-1") long categoryId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
            Model model) {

        log.info("BOARD: {}, CATEGORY: {}, PAGE: {}", id, categoryId, pageNum);

        final List<Board> boards = boardRepository.findEnableBoards();
        final Board board = boardRepository.findById(id).orElse(null);
        if (board == null) {
            throw new ResourceNotFoundException("Not Found Board.");
        }


        final List<Category> categories = categoryRepository.findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(board);

        final Page<Article> pagedArticles;

        if (categoryId == 0) {
            // category non assigned
//            pagedArticles = articleRepository.findAllByCategoryIsNullAndDeletedAtIsNull(PageRequest.of(pageNum - 1, 6, Sort.by("createdAt").descending()));
            pagedArticles = articleRepository.findCategoryNonAssignedArticles(board, PageRequest.of(pageNum - 1, 6, Sort.Direction.DESC, "createdAt"));
        } else if (categoryId > 0) {
            Category selectedCategory = categoryRepository.findById(categoryId).orElse(null);
            pagedArticles = articleRepository.findArticles(board, selectedCategory, PageRequest.of(pageNum - 1, 6, Sort.Direction.DESC, "createdAt"));
        } else {
            pagedArticles = articleRepository.findByBoardAndDeletedAtIsNull(board, PageRequest.of(pageNum - 1, 6, Sort.Direction.DESC, "createdAt"));
        }

//        final List<Article> articles = articleRepository.findTop5ByBoardAndDeletedAtNullOrderByCreatedAtDesc(board);

        model.addAttribute("boards", boards);
        model.addAttribute("board", board);
        model.addAttribute("categories", categories);
        model.addAttribute("page", pagedArticles);
        model.addAttribute("selectedCategoryId", categoryId);

        return "board";
    }


}
