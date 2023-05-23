package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping(value = "/create/{companyId}")
    public String create(
            @PathVariable("companyId") Long companyId,
            Model model){
        model.addAttribute("id", companyId);
        return "card/create";
    }

    @PostMapping(value = "/add")
    public String createCard(
            Model model,
            @RequestParam("id_company") Long idCompany,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("typeOfDiscount") String typeOfDiscount) throws IOException {
        cardService.createCard(file, name, typeOfDiscount, idCompany);
        return "redirect:/company/details/"+idCompany;
    }
}
