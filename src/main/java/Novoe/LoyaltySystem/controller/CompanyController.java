package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

@Autowired
    CompanyService companyService;
    @GetMapping(value = "/create")
    public String createCompany(Model model){
    model.addAttribute("companies", companyService.allCompany());
        return "company/create";
    }

}
