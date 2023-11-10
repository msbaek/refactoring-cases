package pe.msbaek.rfcases.splitabstractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static pe.msbaek.rfcases.splitabstractions.Order.PaymentMethod.CARD;

@ExtendWith(MockitoExtension.class)
class GodTest {
    @Spy private God god;

    @Test
    void high() { // + 5 more tests like this
        doNothing().when(god).low(any(Order.class));
        Order order = orderPaidByCard();

        String result = god.high(order);

        assertThat(result).isEqualTo("bonus");
    }

    private Order orderPaidByCard() {
        Order order = new Order();
        order.setPaymentMethod(CARD);
        return order;
    }
}