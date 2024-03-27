package pe.msbaek.rfcases.refactoring_stream;

import java.util.List;
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
    private final ShippingCalculator shippingCalculator = new ShippingCalculator();

    /**
     * @param itemOptions   검수 단계에서 측정된 실제 무게를 가져오기 위한 아이템 정보
     * @param shippingItems 배송할 아이템 정보
     * @return 전체 무게를 반환.
     */
    public Long calculateTotalWeight(final List<ItemOption> itemOptions, final List<ShippingItem> shippingItems) {
        return shippingCalculator.calculateTotalWeight(itemOptions, shippingItems);
    }
}