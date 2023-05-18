package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping(value = "/create")
    public String create(){
        return "card/create";
    }
}
