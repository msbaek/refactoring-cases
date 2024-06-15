package pe.msbaek.rfcases.valueobject;

import java.util.ArrayList;
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
        List<CartAdditionalItem> list = new ArrayList<>();
        for (AdditionalProduct additionalProduct : additionalProducts) {
            List<CartAdditionalItem> c = mapToAdditionalItems(cartAdditionalItemDtos, additionalProduct);
            list.addAll(c);
        }
        return list;
    }

    private List<CartAdditionalItem> mapToAdditionalItems(List<CartAdditionalItemDto> cartAdditionalItemDtos, AdditionalProduct additionalProduct) {
        List<CartAdditionalItem> c = new ArrayList<>();
        for (CartAdditionalItemDto cartAdditionalItemDto : cartAdditionalItemDtos) {
            if (isaBoolean(additionalProduct, cartAdditionalItemDto)) {
                CartAdditionalItem cartAdditionalItem = getCartAdditionalItem(additionalProduct, cartAdditionalItemDto);
                c.add(cartAdditionalItem);
            }
        }
        return c;
    }

    private CartAdditionalItem getCartAdditionalItem(final AdditionalProduct additionalProduct, final CartAdditionalItemDto cartAdditionalItemDto) {
        return new CartAdditionalItem(additionalProduct.getId().productNo(), additionalProduct.getId().fanClubProductNo(), cartAdditionalItemDto.qty());
    }

    private boolean isaBoolean(final AdditionalProduct additionalProduct, final CartAdditionalItemDto cartAdditionalItemDto) {
        return additionalProduct.getId().productNo().equals(cartAdditionalItemDto.productNo()) && additionalProduct.getId().fanClubProductNo().equals(cartAdditionalItemDto.fanClubProductNo());
    }
}