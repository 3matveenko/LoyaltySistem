package Novoe.LoyaltySystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * Карта клиента.
 */
@Getter
@Setter
@Entity
@Table(name="t_catd_items")
public class CardItem extends BaseEntity{


    /**
     * Шаблон карты
     */
    @OneToOne
    private Card card;

    /**
     * К какой компании принадлежит карта.
     */
    @OneToOne
    private Company company;

}
