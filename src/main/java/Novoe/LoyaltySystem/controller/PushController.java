package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/push")
public class PushController {

    @Autowired
    PushService pushService;


    @GetMapping(value = "/")
    public String allPush(Model model){
        model.addAttribute("messages", pushService.getAllByCompanyId());
        return "push/all";
    }
    @GetMapping(value = "/details/{pushId}")
    public String details(
            Model model,
            @PathVariable("pushId") Long pushId){
        model.addAttribute("push", pushService.getPushById(pushId));
        return "push/details";
    }
    @GetMapping(value = "/create")
    public String create(){
        return "push/create";
    }

    @PostMapping(value = "/add")
    public String add(@RequestParam("text") String text,
                      @RequestParam("title") String title){
    pushService.createPush(title,text);
        return "redirect:/push/";
    }
    @GetMapping(value = "update/{pushId}")
    public String update(
            Model model,
            @PathVariable("pushId") Long pushId){
        model.addAttribute("push", pushService.getPushById(pushId));
        return "push/update";
    }
    @PostMapping(value = "/update")
    public String update(
            @RequestParam("id") Long pushId,
            @RequestParam("text") String text,
            @RequestParam("title") String title){
        pushService.updatePush(pushId,title,text);
        return "redirect:/push/";
    }
    @GetMapping(value = "delete/{pushId}")
    public String delete(
            @PathVariable("pushId") Long pushId){
        pushService.deletePushById(pushId);
        return "redirect:/push/";
    }

}
