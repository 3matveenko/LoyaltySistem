package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Permission;
import Novoe.LoyaltySystem.model.User;
import Novoe.LoyaltySystem.repository.PermissionRepository;
import Novoe.LoyaltySystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CompanyService companyService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public Boolean create(String userName, Long companyId, String email, String password, String repeat) {
        Boolean result = null;
        if (password.equals(repeat)) {
            User user1 = userRepository.findUserByEmail(email);
            if (user1 == null) {
                User user = new User();
                user.setName(userName);
                user.setEmail(email);
                user.setCompany(companyService.findById(companyId));
                user.setPassword(passwordEncoder.encode(password));
                Permission defaultPermission = permissionService.findByName("ROLE_USER");
                List<Permission> newList = new ArrayList<>();
                if (defaultPermission != null) {
                    newList.add(defaultPermission);
                    user.setPermissions(newList);
                    userRepository.save(user);
                    result = true;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public Boolean retype(String oldPassword, String password, String repeat, Long userId) {
        Boolean result = null;
        User user = userRepository.findById(userId).orElseThrow();
            if (password.equals(repeat)) {
                if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                    return true;
                } else {
                    return result;
                }
            }
           return false;
    }
    public long getCount(){
        return userRepository.count();
    }

    public List<User> allUsers(){
       return userRepository.findAll();
    }

    public void update(Long userId, String userName,String email,Long companyId){
        User user = getUserById(userId);
        user.setName(userName);
        user.setEmail(email);
        user.setCompany(companyService.findById(companyId));
        userRepository.save(user);
    }
}