package pe.msbaek.rfcases.tdd_refacotring;

import java.util.List;

public record ApplicationService(LoadWarehousePort loadWarehousePort1, CarrierPort carrierPort1,
                                 LoadInventoryLpnPort loadInventoryLpnPort1) {
    InventoryTransferOrder getInventoryTransferOrder(InventoryTransferOrderRequest request) {
        // when // todo: application 서비스로 extract
        final List<ItemToTransfer> itemsToTransfer = request.getItemsToCreate();
        final Warehouse toWarehouse = loadWarehousePort1().getBy(request.toWarehouseId());
        final Warehouse fromWarehouse = loadWarehousePort1().getBy(request.fromWarehouseId());
        final Carrier carrier = carrierPort1().getBy(request.carrierId());
        final List<InventoryWarehouse> inventoryWarehouses = loadInventoryLpnPort1().findBy(request.fromWarehouseId(), itemsToTransfer.stream().map(ItemToTransfer::itemId).toList());

        //todo: 도메인 서비스로 분리
        itemsToTransfer.forEach(item1 -> inventoryWarehouses.stream()
                .filter(inventory -> inventory.getItemId().equals(item1.itemId()))
                .findFirst()
                .orElseThrow()
                .subtractQuantity(item1.qty()));
        final Shipping shipping = new Shipping(1L, null);
        itemsToTransfer.forEach(item -> shipping.addShippingItem(item.itemId(), item.qty()));
        fromWarehouse.getId();
        final InventoryTransferOrder inventoryTransferOrder = new InventoryTransferOrder(fromWarehouse.getId(), toWarehouse.getId(), shipping);
        return inventoryTransferOrder;
    }
}