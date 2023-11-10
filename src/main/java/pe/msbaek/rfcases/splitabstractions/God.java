package pe.msbaek.rfcases.splitabstractions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class God {
    private final OrderValidator orderValidator;

    public String high(Order order) {
        orderValidator.low(order);
        // complexity requiring 5+ tests
        if (order.getPaymentMethod() == Order.PaymentMethod.CARD) {
            return "bonus";
        }
        return "regular";
    }
}

@Data
class Order {
    enum PaymentMethod {
        CARD, CASH_ON_DELIVERY
    }

    private LocalDate creationDate;
    private PaymentMethod paymentMethod;
}