package Novoe.LoyaltySystem.service;

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

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public Boolean register(String FullName, String email, String password, String repeat) {
        Boolean result = null;
        if (password.equals(repeat)) {
            User user1 = userRepository.findUserByUserName(email);
            if (user1 == null) {
                User user = new User();
                //user.setFullName(FullName);
               // user.setEmail(email);
                user.setPassword(passwordEncoder.encode(password));
                Permission defaultPermission = permissionRepository.findByName("ROLE_USER");
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

    public Boolean retype(String old_password, String password, String repeat) {
        Boolean result = null;
        User user = getUser();
        if (user != null) {
            if (password.equals(repeat)) {
                if (passwordEncoder.matches(old_password, user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                    result = true;
                }
            } else {
                result = false;
            }
        }
        return result;

    }
}