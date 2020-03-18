package net.ujacha.board.api.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaginationUtilsTest {

    @Test
    public void test(){
        assertThat(PaginationUtils.link("/board", "id=2&page=3", "page", 1)).isEqualTo("/board?id=2&page=1");
        assertThat(PaginationUtils.link("/board", "id=2&pages=3", "pages", 1)).isEqualTo("/board?id=2&pages=1");
        assertThat(PaginationUtils.link("/board", "id=2", "page", 1)).isEqualTo("/board?id=2&page=1");
        assertThat(PaginationUtils.link("/board", "", "page", 3)).isEqualTo("/board?page=3");
        assertThat(PaginationUtils.link("/board", null, "page", 3)).isEqualTo("/board?page=3");
        assertThat(PaginationUtils.link("", "id=2&page=3", "page", 1)).isEqualTo("?id=2&page=1");


    }

}