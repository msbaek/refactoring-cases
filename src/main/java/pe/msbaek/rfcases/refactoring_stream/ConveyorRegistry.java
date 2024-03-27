package pe.msbaek.rfcases.refactoring_stream;

import java.util.List;
import java.util.Objects;

interface ItemOption {
    Long getId();

    Long getWeight();
}

interface ShippingItem {
    Long getItemId();

    Long getQty();
}
public class ConveyorRegistry {
    /**
     * @param itemOptions   검수 단계에서 측정된 실제 무게를 가져오기 위한 아이템 정보
     * @param shippingItems 배송할 아이템 정보
     * @return 전체 무게를 반환.
     */
    public Long calculateTotalWeight(final List<ItemOption> itemOptions, final List<ShippingItem> shippingItems) {
        return shippingItems.stream()
                .mapToLong(shippingItem -> {
                    final Long itemId = shippingItem.getItemId();
                    final Long qty = shippingItem.getQty();
                    return itemOptions.stream()
                            .filter(itemOption -> Objects.equals(itemOption.getId(), itemId))
                            .findFirst()
                            .map(ItemOption::getWeight)
                            .orElse(0L) * qty;
                })
                .sum();
    }
}