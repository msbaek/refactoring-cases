package pe.msbaek.rfcases.splitabstractions.mercury;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

interface ProductOptionRepository {
    void save(ProductOption option);
}

interface ProductOptionComboRepository {
    void save(ProductOptionCombo productOptionCombo);

    void saveAll(List<ProductOptionCombo> productOptionCombos);
}

interface ProductOptionValueRepository {
    void save(ProductOptionValue productOptionValue);

    void saveAll(List<ProductOptionValue> productOptionValues);
}

@Getter
class ProductOption {
    private List<ProductOptionValue> optionValues;
    private List<ProductOptionCombo> optionCombos;

    public static ProductOption of(ProductOption option) {
        throw new UnsupportedOperationException("ProductOption::of not implemented yet");
    }
}

class ProductOptionValueId {
    private Long optionNo;
    private Long valueNo;

    public ProductOptionValueId(Long optionNo, Long valueNo) {
        this.optionNo = optionNo;
        this.valueNo = valueNo;
    }
}

class ProductOptionValue {
    private ProductOptionValueId productOptionValueId;
    private String optionValueName;
    private Long referenceProductNo;
    private Float displayOrder;
    private boolean deleted;
    private boolean isDeleteFromCart;

    @Builder
    public ProductOptionValue(ProductOptionValueId productOptionValueId, String optionValueName, Long referenceProductNo, Float displayOrder, boolean deleted, boolean isDeleteFromCart) {
        this.productOptionValueId = productOptionValueId;
        this.optionValueName = optionValueName;
        this.referenceProductNo = referenceProductNo;
        this.displayOrder = displayOrder;
        this.deleted = deleted;
        this.isDeleteFromCart = isDeleteFromCart;
    }

    public static ProductOptionValue of(ProductOptionValue optionValue) {
        throw new UnsupportedOperationException("ProductOptionValue::of not implemented yet");
    }
}

@Getter
class ProductOptionCombo {
    private Long optionComboNo;
    private Long productNo;
    private Long optionNo;
    private Long optionValueNo;
    private Long referenceProductNo;
    private Integer displayOrder;
    private Boolean deleted;

    @Builder
    public ProductOptionCombo(Long optionComboNo, Long productNo, Long optionNo, Long optionValueNo,
                              Long referenceProductNo, Integer displayOrder, Boolean deleted) {
        this.optionComboNo = optionComboNo;
        this.productNo = productNo;
        this.optionNo = optionNo;
        this.optionValueNo = optionValueNo;
        this.referenceProductNo = referenceProductNo;
        this.displayOrder = displayOrder;
        this.deleted = deleted;
    }

    public static ProductOptionCombo of(ProductOptionCombo optionCombo) {
        throw new UnsupportedOperationException("ProductOptionCombo::of not implemented yet");
    }

    public static ProductOptionCombo of(ProductOptionCombo productOptionCombo, Long optionValueNo, Long optionComboNo) {
        throw new UnsupportedOperationException("ProductOptionCombo::of not implemented yet");
    }
}

@Getter
class ProductOptionId {
    private Long optionNo;

    public ProductOptionId(Long productNo, Long optionNo) {
        throw new UnsupportedOperationException("ProductOptionId::ProductOptionId not implemented yet");
    }
}

@RequiredArgsConstructor
public class ProductOptionAdapter {
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionComboRepository optionComboRepository;
    private final ProductOptionValueRepository optionValueRepository;
    private final CreateProductOption createProductOption = new CreateProductOption();

    public void createProductOption(final List<ProductOption> options) {
        options.forEach(option -> {
            CreateProductOption.CreateProductOptionResult result = createProductOption.createProductOption(option);
            saveProductOption(result);
        });
    }

    private void saveProductOption(CreateProductOption.CreateProductOptionResult result) {
        optionComboRepository.saveAll(result.productOptionCombos());
        productOptionRepository.save(result.productOption());
        optionValueRepository.saveAll(result.productOptionValues());
    }
}