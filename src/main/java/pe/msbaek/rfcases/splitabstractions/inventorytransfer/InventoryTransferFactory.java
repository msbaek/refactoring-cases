package pe.msbaek.rfcases.splitabstractions.inventorytransfer;

import java.util.List;

public class InventoryTransferFactory {
    public InventoryTransferFactory() {
    }

    InventoryTransfer createInventoryTransfer(final Request request) {
        final List<InventoryTransferItem> items = request.items().stream()
                .map(item -> new InventoryTransferItem(item.goodsId(), item.requestedQuantity()))
                .toList();

        return new InventoryTransfer(
                request.fromWarehouseId(),
                request.toWarehouseId(),
                request.requestedDate(),
                request.note(),
                items
        );
    }
}