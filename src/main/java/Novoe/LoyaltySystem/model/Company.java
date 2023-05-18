package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="t_companies")
public class Company extends BaseEntity{

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "bin")
    private String bin;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Card> cards;

}
