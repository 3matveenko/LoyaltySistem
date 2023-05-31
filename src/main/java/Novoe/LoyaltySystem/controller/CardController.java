package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CardService;
import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.TypeOfCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TypeOfCardService typeOfCardService;

    @GetMapping(value = "/create/{companyId}")
    public String create(
            @PathVariable("companyId") Long companyId,
            Model model){
        model.addAttribute("allTypeOfCard", typeOfCardService.allTypeOfCard());
        model.addAttribute("id", companyId);
        return "card/create";
    }

    @PostMapping(value = "/add")
    public String createCard(
            @RequestParam("id_company") Long idCompany,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("typeOfDiscount") String typeOfDiscount,
            @RequestParam("typeOfCard") Long typeOfCard) throws IOException {
        cardService.createCard(file, name, typeOfDiscount, idCompany, typeOfCard);
        return "redirect:/company/details/"+idCompany;
    }

    @GetMapping(value = "/details/{cardId}/{companyId}")
    public String details(
            @PathVariable("companyId") Long companyId,
            @PathVariable("cardId") Long cardId,
            Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("card", cardService.findByid(cardId));
        return "card/details";
    }

    @GetMapping(value = "/update/{cardId}/{companyId}")
    public String update(
            @PathVariable("companyId") Long companyId,
            @PathVariable("cardId") Long cardId,
            Model model){
        model.addAttribute("allTypeOfCard", typeOfCardService.allTypeOfCard());
        model.addAttribute("companyId",companyId);
        model.addAttribute("card", cardService.findByid(cardId));
        return "card/update";
    }

    @PostMapping(value = "/update")
    public String update(
            @RequestParam("id_card") String idCard,
            @RequestParam("id_company") String idCompany,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("typeOfDiscount") String typeOfDiscount,
            @RequestParam("typeOfCard") Long typeOfCard) throws IOException {
        cardService.update(file, name, typeOfDiscount,Long.parseLong(idCard), typeOfCard);
        return "redirect:/company/details/"+idCompany;
    }

    @PostMapping(value = "/delete")
    public String delete(
            @RequestParam("companyId") Long idCompany,
            @RequestParam("cardId") Long cardId){
        cardService.delete(cardId, idCompany);
        return "redirect:/company/details/"+idCompany;
    }
}
