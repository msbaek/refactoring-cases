package pe.msbaek.rfcases.refactoring_stream;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        long sum = 0L;
        for (ShippingItem shippingItem : shippingItems) {
            final Long itemId = shippingItem.getItemId();
            final Long qty = shippingItem.getQty();
            Optional<ItemOption> found = getItemOption(itemOptions, itemId);
            long l = found
                    .map(ItemOption::getWeight)
                    .orElse(0L) * qty;
            sum += l;
        }
        return sum;
    }

    private Optional<ItemOption> getItemOption(List<ItemOption> itemOptions, Long itemId) {
        return itemOptions.stream()
                .filter(itemOption -> Objects.equals(itemOption.getId(), itemId))
                .findFirst();
    }
}