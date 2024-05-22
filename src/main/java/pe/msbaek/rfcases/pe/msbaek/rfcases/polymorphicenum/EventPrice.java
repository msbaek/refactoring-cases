package pe.msbaek.rfcases.pe.msbaek.rfcases.polymorphicenum;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
enum DiscountType {
    RATE("rate", "정율") {
        @Override
        BigDecimal eventDiscountPrice(BigDecimal retailPrice, Integer scale, BigDecimal discountValue1) {
            final BigDecimal discountRate = BigDecimal.ONE.subtract(discountValue1.divide(BigDecimal.valueOf(100)));
            return retailPrice.multiply(discountRate).setScale(scale, RoundingMode.CEILING);
        }
    },
    PRICE("price", "정액") {
        @Override
        BigDecimal eventDiscountPrice(BigDecimal retailPrice, Integer scale, BigDecimal discountValue1) {
            return retailPrice.subtract(discountValue1).setScale(scale, RoundingMode.CEILING);
        }
    },
    PRICE_FIX("price.fix", "고정가") {
        @Override
        BigDecimal eventDiscountPrice(BigDecimal retailPrice, Integer scale, BigDecimal discountValue1) {
            return discountValue1.setScale(scale, RoundingMode.CEILING);
        }
    };

    private final String code;
    private final String description;

    DiscountType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    abstract BigDecimal eventDiscountPrice(BigDecimal retailPrice, Integer scale, BigDecimal discountValue1);
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