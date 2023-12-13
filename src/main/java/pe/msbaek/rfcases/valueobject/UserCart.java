package pe.msbaek.rfcases.valueobject;

import java.util.List;

record UserCoupon(String couponCode, float discountRate) {
}

public class UserCart {
    private Shipping shipping;

    public double finalShippingCostKrw(final List<UserCoupon> userCoupons) {
        return (double)Math.round(shipping.costKrw() - calculateCouponDiscounts(userCoupons));
    }

    private float calculateCouponDiscounts(final List<UserCoupon> userCoupons) {
        return userCoupons.stream()
                .map(UserCoupon::discountRate)
                .reduce(0f, Float::sum);
    }
}
