package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс шаблона карты
 */
@Getter
@Setter
@Entity
@Table(name="t_cards")
public class Card extends BaseEntity{

    /**
     * Название карточки
     */
    @Column(name = "name")
    private String name;

    /**
     * Путь к картинке
     */
    @Column(name = "image")
    private String image;

    /**
     * Описание карты, кторое видит клиент в приложении.
     */
    @Column(name = "description")
    private String description;

    /**
     * Статус карты (активна или нет)
     */
    @Column(name = "status")
    private boolean status;

    /**
     * Тип карты бонусная/скидочная
     */
    @OneToOne(fetch = FetchType.EAGER)
    private TypeOfCard typeOfCard;

    /**
     * Тип скидки
     */
    @Column(name = "type_of_discount", columnDefinition = "text")
    private String typeOfDiscount;


}
