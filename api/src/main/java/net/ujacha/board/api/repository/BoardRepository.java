package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findTop1ByOrderByDisplayOrder();

}
