package pe.msbaek.rfcases.pe.msbaek.rfcases.polymorphicenum;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
enum DiscountType {
    RATE("rate", "정율"),
    PRICE("price", "정액"),
    PRICE_FIX("price.fix", "고정가");

    private final String code;
    private final String description;

    DiscountType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    BigDecimal eventDiscountPrice(BigDecimal retailPrice, Integer scale, BigDecimal discountValue1) {
        return switch (this) {
            case RATE -> {
                final BigDecimal discountRate = BigDecimal.ONE.subtract(discountValue1.divide(BigDecimal.valueOf(100)));
                retailPrice.multiply(discountRate).setScale(scale, RoundingMode.CEILING);
            }
            case PRICE -> retailPrice.subtract(discountValue1).setScale(scale, RoundingMode.CEILING);
            case PRICE_FIX -> discountValue1.setScale(scale, RoundingMode.CEILING);
        };
    }
}

public class EventPrice {
    private final DiscountType discountType;
    private final BigDecimal discountValue;

    public EventPrice(final DiscountType discountType,
                      final BigDecimal discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public DiscountType discountType() {
        return this.discountType;
    }

    public BigDecimal discountValue() {
        return this.discountValue;
    }

    public BigDecimal calculateEventDiscountPrice(final BigDecimal retailPrice,
                                                  final Integer scale) {
        return discountType.eventDiscountPrice(retailPrice, scale, discountValue);
    }
}