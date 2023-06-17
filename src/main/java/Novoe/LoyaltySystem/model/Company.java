package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Модель компании
 */
@Getter
@Setter
@Entity
@Table(name="t_companies")
public class Company extends BaseEntity{

    /**
     * Название компании
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * Имя руководителя
     */
    @Column(name = "manager_name")
    private String managerName;

    /**
     * Адрес компании
     */
    @Column(name = "address")
    private String address;

    /**
     * Номер телефона
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Бин компании
     */
    @Column(name = "bin")
    private String bin;

    /**
     * Уникальный токен
     */
    @Column(name = "token")
    private String token;

    /**
     * Карточки компании
     */
    @OneToMany(fetch = FetchType.EAGER)
    private List<Card> cards;

}
