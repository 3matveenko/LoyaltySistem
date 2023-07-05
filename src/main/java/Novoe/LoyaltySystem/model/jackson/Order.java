package Novoe.LoyaltySystem.model.jackson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private String id;

    private List<Item> items;

    private int card_id;
}
