package pe.msbaek.rfcases.tdd_refacotring;

import java.util.List;

public class DomainService {
    public DomainService() {
    }

    Shipping createShipping(List<ItemToTransfer> itemsToTransfer, List<InventoryWarehouse> inventoryWarehouses) {
        itemsToTransfer.forEach(item1 -> inventoryWarehouses.stream()
                .filter(inventory -> inventory.getItemId().equals(item1.itemId()))
                .findFirst()
                .orElseThrow()
                .subtractQuantity(item1.qty()));
        final Shipping shipping = new Shipping(1L, null);
        itemsToTransfer.forEach(item -> shipping.addShippingItem(item.itemId(), item.qty()));
        return shipping;
    }
}