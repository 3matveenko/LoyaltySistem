package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String allCustomers(
            Model model){
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/all";
    }

    @GetMapping("/details/{customerId}")
    public String details(
            @PathVariable Long customerId,
            Model model){
        try {
           model.addAttribute("customer", customerService.findCustomerById(customerId));
        } catch (Exception e) {
            model.addAttribute("noCustomer", true);
            //log
        }
        return "customer/details";
    }
}
