package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/company")
public class CompanyController {

@Autowired
    CompanyService companyService;
    @GetMapping(value = "/")
    public String allCompany(Model model){
    model.addAttribute("companies", companyService.allCompany());
    return "company/all";
    }

    @GetMapping(value = "/create")
    public String createCompanyform(){
        return "company/create";
    }

    @PostMapping(value = "/create")
    public String createCompanyaction(@ModelAttribute Company company){
        companyService.create(company);
        return "redirect:/company/";
    }

    @GetMapping(value = "/delete/{companyId}")
    public String deleteCompany(@PathVariable("companyId") Long companyId){
        companyService.delete(companyId);
        return "redirect:/company/";
    }

    @GetMapping(value = "/details/{companyId}")
    public String detailsCompany(Model model,
            @PathVariable("companyId") Long companyId){
        model.addAttribute("company", companyService.findById(companyId));
        return "/company/details";
    }

    @GetMapping(value = "/update/{companyId}")
    public String updateCompany(Model model,
                                 @PathVariable("companyId") Long companyId){
        model.addAttribute("company", companyService.findById(companyId));
        return "/company/update";
    }
}
