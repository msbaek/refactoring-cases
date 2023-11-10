package pe.msbaek.rfcases.functionalcore;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public class Customer {
    private Long id;
    private String email;
    private String name;
    private String shippingAddress;
    private String billingAddress;
    private List<String> labels = new ArrayList<>();
    private Address address = new Address();
    private Date createDate;
    private List<Coupon> coupons = new ArrayList<>();

    Customer(String shippingAddress, String billingAddress) {
        this.shippingAddress = requireNonNull(shippingAddress);
        this.billingAddress = requireNonNull(billingAddress);
    }

    public Customer() {
    }

    public Customer setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public Customer setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public Customer setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
        return this;
    }
}
