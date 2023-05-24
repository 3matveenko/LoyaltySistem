package Novoe.LoyaltySystem.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
public class UsernameAspect {

    @Before("execution(* Novoe.LoyaltySystem.controller.*.*(..)) && args(model, ..)")
    public void addUsernameToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
    }
}
