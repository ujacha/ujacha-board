package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // TODO 다이나믹 쿼리로 처리
    @Query("select a from Article a where a.board = :board and a.category = :category and a.deletedAt is null")
    Page<Article> findArticles(@Param("board") Board board, @Param("category") Category category, Pageable pageable);

    @Query("select a from Article a where a.board = :board and a.category is null and a.deletedAt is null")
    Page<Article> findCategoryNonAssignedArticles(@Param("board") Board board, Pageable pageable);

    Page<Article> findByBoardAndDeletedAtIsNull(Board board, Pageable pageable);
}
