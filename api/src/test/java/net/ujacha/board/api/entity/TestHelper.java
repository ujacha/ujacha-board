package net.ujacha.board.api.entity;

public class TestHelper {
    static Article createArticle(Board board, Member writer, Category category, String title, String text) {
        return new Article(board, writer, title, text, category);
    }
}