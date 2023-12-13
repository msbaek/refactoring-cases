package pe.msbaek.rfcases.valueobject;

import java.util.List;

public record UserCoupons(List<UserCoupon> userCoupons) {
    float calculateCouponDiscounts() {
        return userCoupons().stream()
                .map(UserCoupon::discountRate)
                .reduce(0f, Float::sum);
    }
}