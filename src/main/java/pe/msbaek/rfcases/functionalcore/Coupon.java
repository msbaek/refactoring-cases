package pe.msbaek.rfcases.functionalcore;

import lombok.RequiredArgsConstructor;

import java.util.Set;
enum ProductCategory {
    ELECTRONICS, KIDS, ME, HOME, UNCATEGORIZED
}

@RequiredArgsConstructor
public class Coupon {
    private final ProductCategory category;
    private final int discountAmount;
    private final Set<Long> applicableSuppliers;
    private boolean autoApply = true;

    public boolean autoApply() {
        return this.autoApply;
    }

    public void setAutoApply(boolean autoApply) {
        this.autoApply = autoApply;
    }

    public boolean isApplicableFor(Product product, Double price) {
        return product.getCategory() == category
                && price > 2.5 * discountAmount
                && applicableSuppliers.contains(product.getSupplier().getId());
    }

    public Double apply(Product product, Double price) {
        if (!isApplicableFor(product, price)) {
            throw new IllegalArgumentException();
        }
        return price - discountAmount;
    }
}
