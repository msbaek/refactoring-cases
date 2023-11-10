package pe.msbaek.rfcases.functionalcore;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PriceService {
    private final CustomerRepo customerRepo;
    private final ThirdPartyPricesApi thirdPartyPricesApi;
    private final CouponRepo couponRepo;
    private final ProductRepo productRepo;

    public Map<Long, Double> computePrices(long customerId, List<Long> productIds,
                                           Map<Long, Double> internalPrices) {
        Customer customer = customerRepo.findById(customerId);
        List<Product> products = productRepo.findAllById(productIds);
        List<Coupon> usedCoupons = new ArrayList<>();
        Map<Long, Double> finalPrices = new HashMap<>();

        extracted(internalPrices, products, customer, usedCoupons, finalPrices);

        couponRepo.markUsedCoupons(customerId, usedCoupons);
        return finalPrices;
    }

    private void extracted(Map<Long, Double> internalPrices, List<Product> products, Customer customer, List<Coupon> usedCoupons, Map<Long, Double> finalPrices) {
        for (Product product : products) {
            Double price = internalPrices.get(product.getId());
            if (price == null) {
                price = thirdPartyPricesApi.fetchPrice(product.getId());
            }
            for (Coupon coupon : customer.getCoupons()) {
                if (coupon.autoApply()
                        && coupon.isApplicableFor(product, price)
                        && !usedCoupons.contains(coupon)) {
                    price = coupon.apply(product, price);
                    usedCoupons.add(coupon);
                }
            }
            finalPrices.put(product.getId(), price);
        }
    }
}