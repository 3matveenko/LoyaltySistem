package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CompanyService companyService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("count", companyService.getCount());
        return "index";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }



}


