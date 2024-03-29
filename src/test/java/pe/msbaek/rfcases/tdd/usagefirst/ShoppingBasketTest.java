package pe.msbaek.rfcases.tdd.usagefirst;

import org.junit.jupiter.api.BeforeEach;
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

    private ShoppingBasket basket;

    @BeforeEach
    void setUp() {
        basket = new ShoppingBasket();
    }

    @Test
    void empty_basket() {
        assertThat(basket.getQuantity("A")).isEqualTo(0);
    }

    @Test
    void one_item_A() {
        BasketItem itemA = BasketItem.of("A", BigDecimal.valueOf(10));
        basket.add(itemA, 1);
        assertThat(basket.getQuantity("A")).isEqualTo(1);
        assertThat(getBigDecimal(basket.calculateTotal())).isEqualTo(getBigDecimal(BigDecimal.valueOf(10.00)));
    }

    @Test
    void two_items_A() {
        BasketItem itemA = BasketItem.of("A", BigDecimal.valueOf(10));
        basket.add(itemA, 2);
        assertThat(basket.getQuantity("A")).isEqualTo(2);
        assertThat(getBigDecimal(basket.calculateTotal())).isEqualTo(getBigDecimal(BigDecimal.valueOf(20.00)));
    }

    @Test
    void two_items_A_and_B() {
        BasketItem itemA = BasketItem.of("A", BigDecimal.valueOf(10));
        BasketItem itemB = BasketItem.of("B", BigDecimal.valueOf(25));
        basket.add(itemA, 1);
        basket.add(itemB, 1);
        assertThat(basket.getQuantity("A")).isEqualTo(1);
        assertThat(basket.getQuantity("B")).isEqualTo(1);
        assertThat(getBigDecimal(basket.calculateTotal())).isEqualTo(getBigDecimal(BigDecimal.valueOf(35.00)));
    }

    private BigDecimal getBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    @DisplayName("총 금액이 $100 초과 시 5% 할인 제공")
    @Disabled("WIP")
    @Test
    void total_over_100_gives_five_percent_discount() {
        ShoppingBasket basket = new ShoppingBasket();
        BasketItem itemA = BasketItem.of("A", BigDecimal.valueOf(10));
        BasketItem itemB = BasketItem.of("B", BigDecimal.valueOf(25));
        BasketItem itemC = BasketItem.of("C", BigDecimal.valueOf(9.99));
        basket.add(itemA, 5);
        basket.add(itemB, 2);
        basket.add(itemC, 6);
        assertThat(basket.getQuantity("C")).isEqualTo(6);
        assertThat(getBigDecimal(basket.calculateTotal())).isEqualTo(getBigDecimal(BigDecimal.valueOf(151.94)));
    }
}