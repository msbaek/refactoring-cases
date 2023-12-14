package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.time.LocalDate;

record Money(int value) {
    public Money(int value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(this.value + money.value);
    }
}

@Getter
public class Order {
    private final DeliveryAddress deliveryAddress = new DeliveryAddress();
    private Long orderId;
    private LocalDate orderDate;

    private Money orderAmount;

    public Money calculateTotalAmount() {
        Money basePrice = getBasePrice();
        return basePrice.add(deliveryAddress.calculateShippingFee());
    }

    private Money getBasePrice() {
        return new Money(10000);
    }

    public Money calculateShippingFee() {
        // 도시 이름에 따라 다른 요금을 책정할 수 있습니다.
        return deliveryAddress.calculateShippingFee();
    }
}