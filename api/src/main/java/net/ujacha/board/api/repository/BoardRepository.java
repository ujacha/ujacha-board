package net.ujacha.board.api.repository;

import net.ujacha.board.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findTop1ByOrderByDisplayOrder();

    @Query("select b from Board b where b.deletedAt is null order by b.displayOrder asc ")
    List<Board> findEnableBoards();

}
