package pe.msbaek.rfcases.loop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//        final List<NewLegacyCartItemRequest> newLegacyCartItemRequests = new ArrayList<>();
//        for (CartItemResponse item : items) {
//            NewLegacyCartItemRequest mainRequest = createMainRequest(item);
//            newLegacyCartItemRequests.add(mainRequest);
//
//            final List<NewLegacyCartItemRequest> additionalRequests = createAdditionalRequests(item);
//            newLegacyCartItemRequests.addAll(additionalRequests);
//        }
//        return newLegacyCartItemRequests;
        return items.stream()
                .flatMap(this::concat)
                .collect(Collectors.toList());
    }

    private Stream<NewLegacyCartItemRequest> concat(final CartItemResponse item) {
        return Stream.concat(
                Stream.of(createMainRequest(item)),
                createAdditionalRequests(item));
    }

    private Stream<NewLegacyCartItemRequest> createAdditionalRequests(final CartItemResponse item) {
        // 2. additionalItems에 대한 NewLegacyCartItemRequest 생성
        return item.additionalItems().stream()
                .map(additionalItem -> createAdditionalRequests(item, additionalItem.additionalProductId()));
    }

    private static NewLegacyCartItemRequest createMainRequest(final CartItemResponse item) {
        // 1. productId를 사용한 NewLegacyCartItemRequest 생성
        NewLegacyCartItemRequest mainRequest = new NewLegacyCartItemRequest(
                item.productId().productNo(),
                null, // parentProductNo는 항상 null
                item.productId().fanClubProductNo(),
                null, // parentFanClubProductNo는 항상 null
                item.productId().bundleNo(),
                item.productId().eventNo()
        );
        return mainRequest;
    }

    private static NewLegacyCartItemRequest createAdditionalRequests(final CartItemResponse item, AdditionalProductId additionalProductId) {
        final NewLegacyCartItemRequest additionalRequest = new NewLegacyCartItemRequest(
                additionalProductId.productNo(),
                item.productId().productNo(), // parentProductNo에 상위 productId의 productNo 할당
                additionalProductId.fanClubProductNo(),
                item.productId().fanClubProductNo(), // parentFanClubProductNo에 상위 productId의 fanClubProductNo 할당
                null, // AdditionalProductId에는 bundleNo가 없음
                item.productId().eventNo()  // additionalProductId에는 eventNo가 없음
        );
        return additionalRequest;
    }
}