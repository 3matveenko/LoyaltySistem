package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if(result==null){
            model.addAttribute("companies", companyService.allCompany());
            model.addAttribute("repeat", true);
            return "user/create";
        }
        if(!result){
            model.addAttribute("companies", companyService.allCompany());
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
    Boolean result = userService.retype(oldPassword,password,repeat,userId);
    if(result==null){
       model.addAttribute("oldPass", true);
        model.addAttribute("userId", userId);
        return "user/change";
    }
    if(result){
        model.addAttribute("ok", true);
        model.addAttribute("userId", userId);

        return "user/change";
    } else {
        model.addAttribute("repeat", true);
        model.addAttribute("userId", userId);

        return "user/change";
    }
    }
    }
