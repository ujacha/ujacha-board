package net.ujacha.board.api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ArticleForm {

    private Long id;

    @NotNull
    private Long boardId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private Long categoryId;

}
