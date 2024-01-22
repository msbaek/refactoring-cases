package pe.msbaek.rfcases.splitabstractions.inventorytransfer;

import java.util.List;

public class CreateInventoryTransferValidator {
    final InventoryTransferPort inventoryTransferPort;
    final LoadWarehousePort loadWarehousePort;

    public CreateInventoryTransferValidator(InventoryTransferPort inventoryTransferPort, LoadWarehousePort loadWarehousePort) {
        this.inventoryTransferPort = inventoryTransferPort;
        this.loadWarehousePort = loadWarehousePort;
    }

    void validate(final Request request) {
        validateWarehouse(request.fromWarehouseId());
        validateWarehouse(request.toWarehouseId());
        validateGoods(request.items());
    }

    void validateWarehouse(final Long warehouseId) {
        loadWarehousePort.findBy(warehouseId)
                .orElseThrow(() -> WarehouseNotFoundException.ofId(warehouseId));
    }

    void validateGoods(final List<CreateInventoryTransferItem> items) {
        final List<Long> goodsIds = getGoodsIds(items);
        for (final Long goodsId : goodsIds) {
            final List<Goods> goods = inventoryTransferPort.listOf(List.of(goodsId));
            if (goods.isEmpty()) throw new IllegalArgumentException("아이템이 존재하지 않습니다. goodsId: " + goodsId);
        }
    }

    List<Long> getGoodsIds(final List<CreateInventoryTransferItem> items) {
        return items.stream()
                .map(CreateInventoryTransferItem::goodsId)
                .toList();
    }
}