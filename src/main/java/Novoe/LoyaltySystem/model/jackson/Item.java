package Novoe.LoyaltySystem.model.jackson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private double amount;
    private double price;
    private String name;
    private double count;

}
