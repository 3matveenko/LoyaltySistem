package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/carditem")
public class CardItemController {

    @Autowired
    CardItemService cardItemService;

    @GetMapping("/")
    public String all(Model model){
        model.addAttribute("carditems", cardItemService.getall());
        return "carditem/all";
    }
}
