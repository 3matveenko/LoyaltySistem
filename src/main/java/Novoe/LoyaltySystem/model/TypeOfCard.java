package Novoe.LoyaltySystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * Тип карты бонусная/скидочная
 */
@Getter
@Setter
@Entity
@Table(name="t_typeofcard")
public class TypeOfCard  extends BaseEntity{

    /**
     * наименование типа карты
     */
    @Column(name = "name")
    private String name;



}
