package Novoe.LoyaltySystem.controller;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Customer;
import Novoe.LoyaltySystem.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
@PreAuthorize("isAuthenticated()")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


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
            logger.info("Exception", e);
        }
        return "customer/details";
    }

    @GetMapping("/update/{customerId}")
    public String update(
            @PathVariable Long customerId,
            Model model){
        try {
            model.addAttribute("customer", customerService.findCustomerById(customerId));
        } catch (Exception e) {
            model.addAttribute("noCustomer", true);
            logger.info("Exception", e);
        }
        return "customer/update";
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute Customer customer){
    customerService.updateCustomer(customer);
    return "redirect:/customer/";
    }
}
