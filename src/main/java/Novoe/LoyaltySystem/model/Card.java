package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="t_cards")
public class Card extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "type_of_discount")
    private String typeOfDiscount;

    @OneToOne(fetch = FetchType.EAGER)
    private TypeOfCard typeOfCard;

}
