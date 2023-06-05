package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * класс админов компаний
 */
@Getter
@Setter
@Entity
@Table(name="t_users")
public class User extends BaseEntity implements UserDetails {

    /**
     * Имя админа
     */
    @Column(name = "user_name")
    private String name;

    /**
     * пароль учетной записи
     */
    @Column(name = "password")
    private String password;

    /**
     * почта учетной записи(используется для авторизации)
     */
    @Column(name = "email")
    private String email;

    /**
     * Компания админа
     */
    @OneToOne
    private Company company;

    /**
     * роль. В приложении есть 2 роли.
     * Admin: создает всех админов,компании.
     * User: ведет учетную запись своей компании
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
