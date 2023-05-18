package Novoe.LoyaltySystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
