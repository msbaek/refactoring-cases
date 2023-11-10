package pe.msbaek.rfcases.splitabstractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static pe.msbaek.rfcases.splitabstractions.Order.PaymentMethod.CARD;

@ExtendWith(MockitoExtension.class)
class GodTest {
    @Mock private OrderValidator orderValidator;
    @InjectMocks private God god;

    @Test
    void high() { // + 5 more tests like this
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