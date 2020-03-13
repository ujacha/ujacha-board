package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Article;
import net.ujacha.board.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findTop5ByBoardAndDeletedAtNullOrderByCreatedAtDesc(Board board);
}
