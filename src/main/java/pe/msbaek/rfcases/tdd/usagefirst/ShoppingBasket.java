package pe.msbaek.rfcases.tdd.usagefirst;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {
    private Map<BasketItem, Integer> items = new HashMap<>();

    public void add(BasketItem item, int quantity) {
        items.put(item, quantity);
    }

    public int getQuantity(String itemName) {
        return items.getOrDefault(new BasketItem(itemName, BigDecimal.ZERO), 0);
    }

    public BigDecimal calculateTotal() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey().price().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
