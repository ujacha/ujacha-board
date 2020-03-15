package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByBoardAndDeletedAtIsNullOrderByDisplayOrderAsc(Board board);
}
