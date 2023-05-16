package Novoe.LoyaltySystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {


    @GetMapping(value = "/create")
    public String createCompany(){
        return "company/create";
    }

}
