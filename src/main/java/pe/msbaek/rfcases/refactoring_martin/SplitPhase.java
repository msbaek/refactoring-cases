package pe.msbaek.rfcases.refactoring_martin;

class Product {
    private double basePrice;
    private double discountThreshold;
    private double discountRate;

    public double getBasePrice() {
        return this.basePrice;
    }

    public double getDiscountThreshold() {
        return this.discountThreshold;
    }

    public double getDiscountRate() {
        return this.discountRate;
    }
}

class ShippingMethod {
    private double discountThreashold;
    private double discountedFee;
    private double feePerCase;

    public double getDiscountThreshold() {
        return this.discountThreashold;
    }

    public double getDiscountedFee() {
        return this.discountedFee;
    }

    public double getFeePerCase() {
        return this.feePerCase;
    }
}
public class SplitPhase {
    public double priceOrder(Product Product, int quantity, ShippingMethod shippingMethod) {
        PriceData priceData = calculatePricingData(Product, quantity);
        return applyShipping(priceData, shippingMethod);
    }

    private PriceData calculatePricingData(Product Product, int quantity) {
        double basePrice = Product.getBasePrice() * quantity;
        double discount = Math.max(quantity - Product.getDiscountThreshold(), 0) * Product.getBasePrice() * Product.getDiscountRate();
        return new PriceData(basePrice, quantity, discount);
    }

    private double applyShipping(PriceData priceData, ShippingMethod shippingMethod) {
        double shippingPerCase = (priceData.basePrice() > shippingMethod.getDiscountThreshold()) ? shippingMethod.getDiscountedFee() : shippingMethod.getFeePerCase();
        double shippingCost = priceData.quantity() * shippingPerCase;
        return priceData.basePrice() - priceData.discount() + shippingCost;
    }

    record PriceData(double basePrice, int quantity, double discount) {}
}