package Novoe.LoyaltySystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }


    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/admin")
    public String adminpanel(){
        return "adminpanel";
    }
}

