package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByBoardAndCategoryAndDeletedAtIsNull(Board board, Category category, Pageable pageable);
    Page<Article> findByBoardAndDeletedAtIsNull(Board board, Pageable pageable);
}
