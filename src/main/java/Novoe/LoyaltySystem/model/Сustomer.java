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
public class Сustomer extends BaseEntity{

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
     * карточки клиента
     */
    @OneToMany
    private List<Card> cards;

    /**
     * компании клиента
     */
    @OneToMany
    private List<Company> company;

}
