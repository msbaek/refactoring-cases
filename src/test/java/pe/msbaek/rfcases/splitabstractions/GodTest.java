package pe.msbaek.rfcases.splitabstractions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static pe.msbaek.rfcases.splitabstractions.Order.PaymentMethod.CARD;

class GodTest {
    private God god;

    @Test
    void high() { // + 5 more tests like this
        Order order = new Order();
        order.setPaymentMethod(CARD);

        String result = god.high(order);

        assertThat(result).isEqualTo("bonus");
    }
}