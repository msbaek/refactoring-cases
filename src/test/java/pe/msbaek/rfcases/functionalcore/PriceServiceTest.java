package pe.msbaek.rfcases.functionalcore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pe.msbaek.rfcases.functionalcore.ProductCategory.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {
    @Mock
    CustomerRepo customerRepo;
    @Mock
    ThirdPartyPricesApi thirdPartyPricesApi;
    @Mock
    CouponRepo couponRepo;
    @Mock
    ProductRepo productRepo;
    @InjectMocks
    PriceService priceService;
    @Captor
    ArgumentCaptor<List<Coupon>> couponCaptor;
    private Coupon homeCoupon;
    private Coupon electrinicsCoupon;
    private List<Coupon> coupons;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        homeCoupon = new Coupon(HOME, 2, Set.of(13L));
        electrinicsCoupon = new Coupon(ELECTRONICS, 4, Set.of(13L));
        coupons = List.of(homeCoupon, electrinicsCoupon);

        Product p1 = new Product(1L).setCategory(HOME).setSupplier(new Supplier(13L));
        Product p2 = new Product(2L).setCategory(KIDS).setSupplier(new Supplier(13L));
        products = List.of(p1, p2);
    }

    @Test
    void computePrices() {
        Customer customer = customersWithCoupons();
        when(customerRepo.findById(13L)).thenReturn(customer);
        when(productRepo.findAllById(List.of(1L, 2L))).thenReturn(products);
        when(thirdPartyPricesApi.fetchPrice(2L)).thenReturn(5d);

        Map<Long, Double> result = priceService.computePrices(13L, List.of(1L, 2L), Map.of(1L, 10d));

        verify(couponRepo).markUsedCoupons(eq(13L), couponCaptor.capture());
        assertThat(couponCaptor.getValue()).containsExactly(homeCoupon);
        assertThat(result)
                .containsEntry(1L, 8d)
                .containsEntry(2L, 5d);
    }

    @Test
    void calculatePrice() {
        Product product = new Product(1L).setCategory(HOME).setSupplier(new Supplier(13L));
        Map<Long, Double> fetchedPrices = Map.of(1L, 10d);
        List<Coupon> coupons = List.of(new Coupon(HOME, 2, Set.of(13L)));
        Customer customer = new Customer().setCoupons(coupons);

        PriceService.PriceAndCoupons priceAndCoupons = PriceService.calculatePrice(product, fetchedPrices, customer);

        assertThat(priceAndCoupons.price()).isEqualTo(8.0d);
        assertThat(priceAndCoupons.usedCoupons().toString()).isEqualTo("[Coupon(category=HOME, discountAmount=2, applicableSuppliers=[13], autoApply=true)]");
    }

    private Customer customersWithCoupons() {
        return new Customer().setCoupons(coupons);
    }
}