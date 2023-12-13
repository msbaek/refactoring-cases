package pe.msbaek.rfcases.valueobject;

import java.util.List;

record UserCoupon(String couponCode, float discountRate) {
}

public class UserCart {
    private Shipping shipping;

    public double finalShippingCostKrw(final List<UserCoupon> userCoupons) {
        return (double)Math.round(shipping.costKrw() - new UserCoupons(userCoupons).calculateCouponDiscounts());
    }
}
