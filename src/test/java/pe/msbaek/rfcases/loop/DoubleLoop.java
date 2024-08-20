package pe.msbaek.rfcases.loop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

record NewLegacyCartItemRequest(Long aLong, Object o, Long aLong1, Object o1, Long aLong2, Long aLong3) {
}

record CartItemResponse(ProductIdResponse productId, Collection<CartAdditionalItemResponse> additionalItems) {
}

record CartAdditionalItemResponse(AdditionalProductId additionalProductId) {
}

record ProductIdResponse(Long productNo, Long fanClubProductNo, Long bundleNo, Long eventNo) {
}

record AdditionalProductId(Long productNo,Long fanClubProductNo) {
}

public class DoubleLoop {
    private Collection<CartItemResponse> items;
    public List<NewLegacyCartItemRequest> extractNewCartItemRequests() {
        final List<NewLegacyCartItemRequest> newLegacyCartItemRequests = new ArrayList<>();
        for (CartItemResponse item : items) {
            // 1. productId를 사용한 NewLegacyCartItemRequest 생성
            ProductIdResponse productId = item.productId();
            NewLegacyCartItemRequest mainRequest = new NewLegacyCartItemRequest(
                    productId.productNo(),
                    null, // parentProductNo는 항상 null
                    productId.fanClubProductNo(),
                    null, // parentFanClubProductNo는 항상 null
                    productId.bundleNo(),
                    productId.eventNo()
            );
            newLegacyCartItemRequests.add(mainRequest);

            // 2. additionalItems에 대한 NewLegacyCartItemRequest 생성
            for (CartAdditionalItemResponse additionalItem : item.additionalItems()) {
                final AdditionalProductId additionalProductId = additionalItem.additionalProductId();
                final NewLegacyCartItemRequest additionalRequest = new NewLegacyCartItemRequest(
                        additionalProductId.productNo(),
                        productId.productNo(), // parentProductNo에 상위 productId의 productNo 할당
                        additionalProductId.fanClubProductNo(),
                        productId.fanClubProductNo(), // parentFanClubProductNo에 상위 productId의 fanClubProductNo 할당
                        null, // AdditionalProductId에는 bundleNo가 없음
                        productId.eventNo()  // additionalProductId에는 eventNo가 없음
                );
                newLegacyCartItemRequests.add(additionalRequest);
            }
        }
        return newLegacyCartItemRequests;
    }
}