package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.model.User;
import Novoe.LoyaltySystem.service.ForgotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/forgot")
public class ForgotController {

    @Autowired
    ForgotService forgotService;

    @GetMapping(value = "/")
    public String index() {
        return "forgot/enteremail";
    }

    @PostMapping(value = "/email")
    public String email(
            Model model,
            @RequestParam("email") String email) {
        User response = forgotService.searchUser(email);
        if(response!=null){
            return "forgot/ok";
        }
        model.addAttribute("mail",true);
        return "forgot/enteremail";
    }

}
