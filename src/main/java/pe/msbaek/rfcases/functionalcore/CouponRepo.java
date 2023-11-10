package pe.msbaek.rfcases.functionalcore;

import java.util.List;

public interface CouponRepo {
    void markUsedCoupons(long customerId, List<Coupon> usedCoupons);
}
