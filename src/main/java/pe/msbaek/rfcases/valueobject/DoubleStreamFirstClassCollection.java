package pe.msbaek.rfcases.valueobject;

import java.util.List;

interface AdditionalProduct {
    ProductId getId();

    record ProductId(Long productNo, Long fanClubProductNo) {
    }
}

interface CartAdditionalItemDto {
    long qty();

    Long productNo();

    Long fanClubProductNo();
}

record CartAdditionalItem(Long productNo, Long fanClubProductNo, long qty) {
}


public class DoubleStreamFirstClassCollection {
    private List<CartAdditionalItem> getCartAdditionalItems(final List<AdditionalProduct> additionalProducts, final List<CartAdditionalItemDto> cartAdditionalItemDtos) {
        return additionalProducts.stream()
                .flatMap(additionalProduct -> cartAdditionalItemDtos.stream()
                        .filter(cartAdditionalItemDto -> isaBoolean(additionalProduct, cartAdditionalItemDto))
                        .map(cartAdditionalItemDto -> getCartAdditionalItem(additionalProduct, cartAdditionalItemDto)))
                .toList();
    }

    private CartAdditionalItem getCartAdditionalItem(final AdditionalProduct additionalProduct, final CartAdditionalItemDto cartAdditionalItemDto) {
        return new CartAdditionalItem(additionalProduct.getId().productNo(), additionalProduct.getId().fanClubProductNo(), cartAdditionalItemDto.qty());
    }

    private boolean isaBoolean(final AdditionalProduct additionalProduct, final CartAdditionalItemDto cartAdditionalItemDto) {
        return additionalProduct.getId().productNo().equals(cartAdditionalItemDto.productNo()) && additionalProduct.getId().fanClubProductNo().equals(cartAdditionalItemDto.fanClubProductNo());
    }
}