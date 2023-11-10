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

        Map<Long, Double> fetchedPrices = new HashMap<>();
        for (Product product : products) {
            Double price = internalPrices.get(product.getId());
            if (price == null) {
                price = thirdPartyPricesApi.fetchPrice(product.getId());
            }
            fetchedPrices.put(product.getId(), price);
        }

        for (Product product : products) {
            Double price = fetchedPrices.get(product.getId());
            List<Coupon> tempCoupons = new ArrayList<>();
            for (Coupon coupon : customer.getCoupons()) {
                if (coupon.autoApply()
                        && coupon.isApplicableFor(product, price)
                        && !tempCoupons.contains(coupon)) {
                    price = coupon.apply(product, price);
                    tempCoupons.add(coupon);
                }
            }
            usedCoupons.addAll(tempCoupons);
            finalPrices.put(product.getId(), price);
        }

        couponRepo.markUsedCoupons(customerId, usedCoupons);
        return finalPrices;
    }
}