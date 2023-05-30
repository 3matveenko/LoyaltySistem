package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotService {

    @Autowired
    UserService userService;

    public User searchUser(String email){
      User user = userService.loadUserByUsername(email);
        if (user!=null){
            return user;
        } else {
            return null;
        }
    }
}
