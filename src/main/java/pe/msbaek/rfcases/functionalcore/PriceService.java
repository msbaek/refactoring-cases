package pe.msbaek.rfcases.functionalcore;

import com.google.common.annotations.VisibleForTesting;
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
            PriceAndCoupons result = calculatePrice(product, fetchedPrices, customer);
            usedCoupons.addAll(result.usedCoupons());
            finalPrices.put(product.getId(), result.price());
        }

        couponRepo.markUsedCoupons(customerId, usedCoupons);
        return finalPrices;
    }

    @VisibleForTesting
    static PriceAndCoupons calculatePrice(Product product, Map<Long, Double> fetchedPrices, Customer customer) {
        Double price = fetchedPrices.get(product.getId());
        List<Coupon> usedCoupons = new ArrayList<>();
        for (Coupon coupon : customer.getCoupons()) {
            if (coupon.autoApply()
                    && coupon.isApplicableFor(product, price)
                    && !usedCoupons.contains(coupon)) {
                price = coupon.apply(product, price);
                usedCoupons.add(coupon);
            }
        }
        return new PriceAndCoupons(price, usedCoupons);
    }

    record PriceAndCoupons(Double price, List<Coupon> usedCoupons) {
    }
}