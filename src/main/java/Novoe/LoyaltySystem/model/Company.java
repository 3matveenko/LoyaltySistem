package Novoe.LoyaltySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="t_companies")
public class Company extends BaseEntity{

    @Column(name = "company_name")
    private String companyName;
}
