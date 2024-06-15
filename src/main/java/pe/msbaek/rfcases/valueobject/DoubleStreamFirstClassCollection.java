package pe.msbaek.rfcases.valueobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        CartItemList cartItemList = CartItemList.from(cartAdditionalItemDtos);
        return getCartAdditionalItems(additionalProducts, cartItemList);
    }

    private List<CartAdditionalItem> getCartAdditionalItems(List<AdditionalProduct> additionalProducts, CartItemList cartItemList) {
        return additionalProducts.stream()
                .map(cartItemList::mapToAdditionalItems)
                .flatMap(Collection::stream)
                .toList();
    }

    private static record CartItemList(List<CartAdditionalItemDto> cartAdditionalItemDtos) {
        private static CartItemList from(List<CartAdditionalItemDto> cartAdditionalItemDtos) {
            return new CartItemList(cartAdditionalItemDtos);
        }

        private List<CartAdditionalItem> mapToAdditionalItems(AdditionalProduct additionalProduct) {
            return this.cartAdditionalItemDtos().stream()
                    .filter(cartAdditionalItemDto -> equals(additionalProduct, cartAdditionalItemDto))
                    .map(cartAdditionalItemDto -> createCartAdditionalItem(additionalProduct, cartAdditionalItemDto))
                    .toList();
        }

        private CartAdditionalItem createCartAdditionalItem(AdditionalProduct additionalProduct, CartAdditionalItemDto cartAdditionalItemDto) {
            return new CartAdditionalItem(additionalProduct.getId().productNo(), additionalProduct.getId().fanClubProductNo(), cartAdditionalItemDto.qty());
        }

        private boolean equals(AdditionalProduct additionalProduct, CartAdditionalItemDto cartAdditionalItemDto) {
            return additionalProduct.getId().productNo().equals(cartAdditionalItemDto.productNo()) && additionalProduct.getId().fanClubProductNo().equals(cartAdditionalItemDto.fanClubProductNo());
        }
    }
}