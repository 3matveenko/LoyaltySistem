package Novoe.LoyaltySystem.model.jackson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private int amount;
    private int price;
    private String name;
    private int count;

}
