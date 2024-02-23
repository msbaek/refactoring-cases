package pe.msbaek.rfcases.tdd.usagefirst;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingBasketTest {
    @DisplayName("총 금액이 $100 초과 시 5% 할인 제공")
    @Disabled("WIP")
    @Test
    void total_over_100_gives_five_percent_discount() {
        ShoppingBasket basket = new ShoppingBasket();
        BasketItem itemA = new BasketItem("A", BigDecimal.valueOf(10));
        BasketItem itemB = new BasketItem("B", BigDecimal.valueOf(25));
        BasketItem itemC = new BasketItem("C", BigDecimal.valueOf(9.99));
        basket.add(itemA, 5);
        basket.add(itemB, 2);
        basket.add(itemC, 6);
        assertThat(basket.getQuantity("C")).isEqualTo(6);
        assertThat(basket.calculateTotal().setScale(2, RoundingMode.HALF_UP)).isEqualTo(151.94);
    }
}