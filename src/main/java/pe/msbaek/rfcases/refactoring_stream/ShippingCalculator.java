package pe.msbaek.rfcases.refactoring_stream;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShippingCalculator {
    public ShippingCalculator() {
    }

    /**
     * @param itemOptions   검수 단계에서 측정된 실제 무게를 가져오기 위한 아이템 정보
     * @param shippingItems 배송할 아이템 정보
     * @return 전체 무게를 반환.
     */
    public Long calculateTotalWeight(final List<ItemOption> itemOptions, final List<ShippingItem> shippingItems) {
        return shippingItems.stream()
                .map(shippingItem -> subTotal(itemOptions, shippingItem))
                .reduce(0L, Long::sum);
    }

    long subTotal(List<ItemOption> itemOptions, ShippingItem shippingItem) {
        return getItemOption(itemOptions, shippingItem.getItemId())
                .map(ItemOption::getWeight)
                .map(weight -> weight * shippingItem.getQty())
                .orElse(0L);
    }

    Optional<ItemOption> getItemOption(List<ItemOption> itemOptions, Long itemId) {
        return itemOptions.stream()
                .filter(itemOption -> Objects.equals(itemOption.getId(), itemId))
                .findFirst();
    }
}