package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Push;
import Novoe.LoyaltySystem.repository.PushRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PushService {

    @Autowired
    PushRepository pushRepository;

    @Autowired
    UserService userService;

    public List<Push> getAllByCompanyId(){
        return pushRepository.findPushByCompany(userService.getUser().getCompany());
    }

    public void createPush(String title, String text){
        Push push = new Push();
        push.setTitle(title);
        push.setText(text);
        push.setCompany(userService.getUser().getCompany());
        pushRepository.save(push);
    }

}
