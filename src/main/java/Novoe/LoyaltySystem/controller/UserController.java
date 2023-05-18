package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping(value = "/")
    public String allCompany(Model model){
        model.addAttribute("users", userService.allUsers());
        return "user/all";
    }
}
