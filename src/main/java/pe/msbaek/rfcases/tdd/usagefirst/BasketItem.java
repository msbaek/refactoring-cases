package pe.msbaek.rfcases.tdd.usagefirst;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "itemName")
public class BasketItem {
    private final String itemName;
    private final BigDecimal price;

    public BasketItem(String itemName, BigDecimal price) {
        this.itemName = itemName;
        this.price = price;
    }
}