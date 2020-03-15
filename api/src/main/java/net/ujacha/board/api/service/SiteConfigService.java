package net.ujacha.board.api.service;

import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteConfigService {

    private final BoardRepository boardRepository;

    public Board getDefaultBoard(){
        return boardRepository.findTop1ByOrderByDisplayOrder();
    }

}
