package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;
    @GetMapping(value = "/")
    public String allCompany(Model model){
        model.addAttribute("users", userService.allUsers());
        return "user/all";
    }

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("companies", companyService.allCompany());
        return "user/create";
    }

    @GetMapping(value = "/update/{userId}")
    public String update(Model model,
                         @PathVariable("userId") Long userId){
        model.addAttribute("companies", companyService.allCompany());
        model.addAttribute("user", userService.getUserById(userId));
        return "user/update";
    }

    @PostMapping(value = "/update")
    public String updateUser(
                         @RequestParam("userId") Long userId,
                         @RequestParam("name") String userName,
                         @RequestParam("email") String email,
                         @RequestParam("company") Long companyId){
        userService.update(userId,userName,email,companyId);
        return "redirect:/user/";
    }

    @PostMapping(value = "/create")
    public String create(
            Model model,
            @RequestParam("userName") String userName,
            @RequestParam("company") Long companyId,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("repeat") String repeat
            ){
        Boolean result = userService.create(userName,companyId,email, password, repeat);
        model.addAttribute("companies", companyService.allCompany());

        if(result==null){
            model.addAttribute("repeat", true);
            return "user/create";
        }
        if(!result){
            model.addAttribute("email", true);
            return "user/create";
        } else {
            return "redirect:/user/";
        }
    }

    @GetMapping(value = "/details/{userId}")
    public String details(
            Model model,
            @PathVariable("userId") Long userId){
        model.addAttribute("user", userService.getUserById(userId));
        return "user/details";
    }

    @GetMapping(value = "/personal")
    public String personal(
            HttpSession session,
            Model model){
       Long id =(Long) session.getAttribute("userID");
        return "redirect:details/"+id;
    }

    @GetMapping(value = "/change/{userId}")
    public String change(Model model,
            @PathVariable("userId") Long userId){
        model.addAttribute("userId", userId);
        return "user/change";
    }

    @PostMapping(value = "/change")
    public String changePassword(
            Model model,
            @RequestParam("old_password") String oldPassword,
            @RequestParam("password") String password,
            @RequestParam("repeat") String repeat,
            @RequestParam("userId") Long userId){
    int result = userService.retype(oldPassword,password,repeat,userId);
    model.addAttribute("userId", userId);
    String response[] = {"repeat","oldPass","ok"};
    model.addAttribute(response[result], true);
    return "user/change";
    }

    @GetMapping(value = "/delete/{userId}")
    public String delete(
            @PathVariable("userId") Long userId){
        userService.delete(userId);
        return "redirect:/user/";
    }
  }
