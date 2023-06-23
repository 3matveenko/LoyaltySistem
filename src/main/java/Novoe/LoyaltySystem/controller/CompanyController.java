package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.service.CardService;
import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.CustomerService;
import Novoe.LoyaltySystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @GetMapping(value = "/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllCompany(Model model){
    model.addAttribute("companies", companyService.getAllCompany());
    return "company/all";
    }
    @GetMapping(value = "/details/{companyId}")
    public String detailsCompany(Model model,
                                 @PathVariable("companyId") Long companyId){
        model.addAttribute("customers", customerService.getCustomersByCompanyId(companyId));
        model.addAttribute("countCard", cardService.getCount(companyId));
        model.addAttribute("company", companyService.findById(companyId));
        try {
            model.addAttribute("cardtocompany", companyService.cardToCompany(companyId));
            logger.info("error");
        } catch (Exception e) {
            model.addAttribute("errorcompanyid", true);
            logger.info("error");
        }
        return "/company/details";
    }

    @GetMapping(value = "/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String createCompanyform(){
        return "company/create";
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String createCompany(@ModelAttribute Company company){
        companyService.create(company);
        return "redirect:/company/";
    }

    @PostMapping(value = "/update")
    public String updateCompany(@ModelAttribute Company company){
        companyService.update(company);
        return "redirect:/company/details/"+company.getId();
    }
    @GetMapping(value = "/update/{companyId}")
    public String updateCompany(Model model,
                                 @PathVariable("companyId") Long companyId){
        model.addAttribute("company", companyService.findById(companyId));
        return "/company/update";
    }
    @GetMapping(value = "/delete/{companyId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCompany(@PathVariable("companyId") Long companyId){
        companyService.delete(companyId);
        return "redirect:/company/";
    }

    @GetMapping(value = "/mycompany")
    public String myCompany(HttpSession session){
        Long id = userService.getCompanyIdByUserId((Long) session.getAttribute("userID"));
        return "redirect:/company/details/"+id;
    }
}
