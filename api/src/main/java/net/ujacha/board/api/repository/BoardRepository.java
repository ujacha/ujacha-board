package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findTop1ByOrderByDisplayOrder();

    List<Board> findAllByDeletedAtIsNullOrderByDisplayOrderAsc();

}
