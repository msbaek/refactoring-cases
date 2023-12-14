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
    private Long orderId;
    private LocalDate orderDate;

    private String street;
    private String city;
    private String zipCode;

    private Money orderAmount;

    public Money calculateTotalAmount() {
        Money basePrice = getBasePrice();
        return basePrice.add(calculateShippingFee());
    }

    private Money getBasePrice() {
        return new Money(10000);
    }

    public Money calculateShippingFee() {
        // 도시 이름에 따라 다른 요금을 책정할 수 있습니다.
        if (this.city.equalsIgnoreCase("서울")) {
            return new Money(5000); // 서울 지역 배송 요금
        }
        else if (this.city.equalsIgnoreCase("부산")) {
            return new Money(7000); // 부산 지역 배송 요금
        }
        else {
            return new Money(10000); // 기타 지역 배송 요금
        }
    }
}