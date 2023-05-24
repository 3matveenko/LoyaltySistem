package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="t_typeofcard")
public class TypeOfCard  extends BaseEntity{

    @Column(name = "name")
    private String name;



}
