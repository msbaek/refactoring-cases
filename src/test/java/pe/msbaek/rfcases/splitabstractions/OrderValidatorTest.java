package pe.msbaek.rfcases.splitabstractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrderValidatorTest {
    private OrderValidator orderValiator;

    @BeforeEach
    void setUp() {
        orderValiator = new OrderValidator();
    }

    @Test
    void low() { // + 5 more tests like this
        Order oldOrder = new Order();
        oldOrder.setCreationDate(now().minusMonths(2));
        assertThatThrownBy(() -> orderValiator.low(oldOrder))
                .hasMessageContaining("old");
    }
}