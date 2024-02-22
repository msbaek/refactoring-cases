package pe.msbaek.rfcases.splitabstractions.mercury;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateProductOption {
    public CreateProductOption() {
    }

    CreateProductOptionResult createProductOption(ProductOption option) {
        final ProductOption productOption = ProductOption.of(option);
        // createProductOptionCombo(option.getOptionCombos());
        List<ProductOptionCombo> productOptionCombos = new ArrayList<ProductOptionCombo>();
        if (!Objects.isNull(option.getOptionCombos())) {
            option.getOptionCombos().forEach(optionCombo -> {
                final ProductOptionCombo productOptionCombo = ProductOptionCombo.of(optionCombo);
                productOptionCombos.add(productOptionCombo);
            });
        }
        // createProductOptionValue(option.getOptionValues());
        List<ProductOptionValue> productOptionValues = new ArrayList<ProductOptionValue>();
        option.getOptionValues().forEach(optionValue -> {
            final ProductOptionValue productOptionValue = ProductOptionValue.of(optionValue);
            productOptionValues.add(productOptionValue);
        });
        return new CreateProductOptionResult(productOption, productOptionCombos, productOptionValues);
    }

    record CreateProductOptionResult(ProductOption productOption, List<ProductOptionCombo> productOptionCombos,
                                             List<ProductOptionValue> productOptionValues) {
    }
}