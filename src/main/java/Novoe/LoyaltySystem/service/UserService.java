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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

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
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByEmail(String email){
       return userRepository.findUserByEmail(email);
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
                    createWelcomeMessage(userName,password,email);
                    result = true;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public Integer retype(String oldPassword, String password, String repeat, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
            if (password.equals(repeat)) {
                if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                    return 2;
                } else {
                    return 1;
                }
            }
           return 0;
    }

    public void delete(Long userId){
        userRepository.delete(getUserById(userId));
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

    public Boolean createWelcomeMessage(String name, String password, String email){
       String header =  "Вы успешно зарегистрировались в системе novocards.";
       String text = "Добрый день "+name+"! Вы успешно зарегистрировались в системе!"+" " +
               "Данные для авторизации: \n" +
               "логин: "+email+"\n" +
               "пароль: "+password;
      return sendmessage(email,header,text);
    }
    public Boolean sendmessage(String email,String header,String text){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.yandex.kz"); // адрес почтового сервера
        properties.put("mail.smtp.port", "465"); // порт почтового сервера
        properties.put("mail.smtp.auth", "true"); // требуется ли аутентификация
        properties.put("mail.smtp.starttls.enable", "true"); // использовать ли STARTTLS для защищенного соединения
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("parohodkz@yandex.kz", "gyrmuypurcxdepov");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("parohodkz@yandex.kz", "Novocards", "UTF-8")); // адрес отправителя
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // адрес получателя
            message.setSubject(header); // тема письма
            message.setText(text); // текст письма
            Transport.send(message);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void deletePassword(String email,String newPassword){
        User user = getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public Long getCompanyIdByUserId(Long UserId){
       User user = getUserById(UserId);
       return user.getCompany().getId();
    }


}