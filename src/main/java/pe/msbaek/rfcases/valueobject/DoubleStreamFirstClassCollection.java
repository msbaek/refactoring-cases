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
            List<CartAdditionalItem> c = mapToAdditionalItems(new CartItemList(cartAdditionalItemDtos), additionalProduct);
            list.addAll(c);
        }
        return list;
    }

    private List<CartAdditionalItem> mapToAdditionalItems(CartItemList cartItemList, AdditionalProduct additionalProduct) {
        List<CartAdditionalItem> c = new ArrayList<>();
        for (CartAdditionalItemDto cartAdditionalItemDto : cartItemList.cartAdditionalItemDtos()) {
            if (additionalProduct.getId().productNo().equals(cartAdditionalItemDto.productNo()) && additionalProduct.getId().fanClubProductNo().equals(cartAdditionalItemDto.fanClubProductNo())) {
                CartAdditionalItem cartAdditionalItem = new CartAdditionalItem(additionalProduct.getId().productNo(), additionalProduct.getId().fanClubProductNo(), cartAdditionalItemDto.qty());
                c.add(cartAdditionalItem);
            }
        }
        return c;
    }

    private static record CartItemList(List<CartAdditionalItemDto> cartAdditionalItemDtos) {
    }
}