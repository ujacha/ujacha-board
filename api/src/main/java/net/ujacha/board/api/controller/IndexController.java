package net.ujacha.board.api.controller;

import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.Board;
import net.ujacha.board.api.service.SiteConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final SiteConfigService siteConfigService;

    @GetMapping("/")
    public String indexRedirect(RedirectAttributes redirectAttributes) {
        final Board defaultBoard = siteConfigService.getDefaultBoard();
        redirectAttributes.addAttribute("id", defaultBoard.getId());
        return "redirect:/board";
    }
}
