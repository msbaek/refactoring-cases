package pe.msbaek.rfcases.splitabstractions;

import java.time.LocalDate;

public class OrderValidator {
    public OrderValidator() {
    }

    void low(Order order) {
        // complexity requiring 5+ tests
        if (order.getCreationDate().isBefore(LocalDate.now().minusMonths(1))) {
            throw new IllegalArgumentException("Order too old");
        }
    }
}