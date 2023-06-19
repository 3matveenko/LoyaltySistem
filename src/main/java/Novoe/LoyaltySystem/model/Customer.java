package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Клиент компании.
 */
@Getter
@Setter
@Entity
@Table(name="t_customers")
public class Customer extends BaseEntity{

    /**
     * ФИО клиента
     */
    @Column(name = "name")
    private String name;

    /**
     * Почта клиента
     */
    @Column(name = "mail")
    private String email;

    /**
     * номер телефона
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * день рождения
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * Токен авторизации
     */
    @Column(name = "token")
    private String token;

    /**
     * карточки клиента
     */
    @OneToMany
    private List<CardItem> cardItems;

    /**
     * компании клиента
     */
    @OneToMany
    private List<Company> company;

}
