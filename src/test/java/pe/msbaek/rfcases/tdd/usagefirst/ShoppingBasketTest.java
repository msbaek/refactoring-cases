package pe.msbaek.rfcases.tdd.usagefirst;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement tests in this order:
 * <p>
 * Empty basket - basket contains no items
 * one item "A" - basket contains 1 item "A"
 * two items "A" - basket contains 2 items "A"
 * two items, "A" and "B" - basket contains 1 item "A"
 * Empty basket - total price 0
 * "A" costs $10, basket contains one "A" - total $10
 * "D" costs $160, basket contains one "D" - total $152 (5% discount)
 * "E" costs $250, basket contains one "E" - total $225 (10% discount)
 * Then remove the "Ignore" marking on the test that's here and hopefully it will pass!
 */
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