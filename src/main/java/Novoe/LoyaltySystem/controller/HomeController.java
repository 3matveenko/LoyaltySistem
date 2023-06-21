package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.model.User;
import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String index(Model model,
                        HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = (User) userService.loadUserByUsername(email);
        session.setAttribute("userID", user.getId());
        session.setAttribute("userNAME", user.getName());
        model.addAttribute("countCompany", companyService.getCount());
        model.addAttribute("coutnUser", userService.getCount());
        return "index";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }


    //удалить
    @GetMapping(value = "/boots")
    public String bootstrap(){
        return "bootstrap";
    }

}


