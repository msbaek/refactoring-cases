package pe.msbaek.rfcases.functionalcore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class Product {
    private long id;
    private Supplier supplier;
    private String name;
    private String sku;
    private ProductCategory category;
    private LocalDate createDate;

    public Product(long id) {
        this.id = id;
    }

    public Product(Supplier supplier, String name, String sku, ProductCategory category, LocalDate createDate) {
        this.supplier = supplier;
        this.name = name;
        this.sku = sku;
        this.category = category;
        this.createDate = createDate;
    }

    public Product setCategory(ProductCategory productCategory) {
        this.category = productCategory;
        return this;
    }

    public Product setSupplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }
}