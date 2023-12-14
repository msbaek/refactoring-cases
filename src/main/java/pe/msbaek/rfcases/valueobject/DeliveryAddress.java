package pe.msbaek.rfcases.valueobject;

public class DeliveryAddress {
    String street;
    String city;
    String zipCode;

    public DeliveryAddress() {
    }

    public Money calculateShippingFee() {
        // 도시 이름에 따라 다른 요금을 책정할 수 있습니다.
        if (this.city.equalsIgnoreCase("서울")) {
            return new Money(5000); // 서울 지역 배송 요금
        } else if (this.city.equalsIgnoreCase("부산")) {
            return new Money(7000); // 부산 지역 배송 요금
        } else {
            return new Money(10000); // 기타 지역 배송 요금
        }
    }
}