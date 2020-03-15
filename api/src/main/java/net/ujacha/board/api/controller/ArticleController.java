package net.ujacha.board.api.controller;

import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Category;
import net.ujacha.board.api.exception.ResourceNotFoundException;
import net.ujacha.board.api.repository.ArticleRepository;
import net.ujacha.board.api.repository.CategoryRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

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

}
