package pe.msbaek.rfcases.tdd_refacotring;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

record ShippingItem(Long itemId, Long qty) {
}

record Shipping(Long id, List<ShippingItem> shippingItems) {
    public List<ShippingItem> getShippingItems() {
        throw new UnsupportedOperationException("Shipping::getShippingItems not implemented yet");
    }

    public void addShippingItem(Long aLong, Long qty) {
        throw new UnsupportedOperationException("Shipping::addShippingItem not implemented yet");
    }
}

record InventoryTransferOrder(Long fromWarehouseId, Long toWarehouseId, Shipping shipping) {
    public Long getFromWarehouseId() {
        throw new UnsupportedOperationException("InventoryTransferOrder::getFromWarehouseId not implemented yet");
    }

    public Long getToWarehouseId() {
        throw new UnsupportedOperationException("InventoryTransferOrder::getToWarehouseId not implemented yet");
    }

    public Shipping getShipping() {
        throw new UnsupportedOperationException("InventoryTransferOrder::getShipping not implemented yet");
    }
}

record InventoryWarehouse(Long itemId, Long quantity, Long warehouseId) {
    public void subtractQuantity(Long qty) {
        throw new UnsupportedOperationException("InventoryWarehouse::subtractQuantity not implemented yet");
    }

    public Long getItemId() {
        throw new UnsupportedOperationException("InventoryWarehouse::getItemId not implemented yet");
    }
}

record Warehouse(Long id) {
    public Long getId() {
        throw new UnsupportedOperationException("Warehouse::getId not implemented yet");
    }
}

record Carrier(Long id) {
}

record ItemToTransfer(Long itemId, Long qty) {
}

record InventoryTransferOrderRequest(Long fromWarehouseId, Long toWarehouseId, Instant shippingDate, Long carrierId, String note, List<InventoryTransferOrderItemRequest> items) {
    public List<ItemToTransfer> getItemsToCreate() {
        throw new UnsupportedOperationException("InventoryTransferOrderRequest::getItemsToCreate not implemented yet");
    }
}

record InventoryTransferOrderItemRequest(Long itemId, Long qty) {
}

interface LoadWarehousePort {
    Warehouse getBy(Long warehouseId);
}

interface CarrierPort {
    Carrier getBy(Long carrierId);
}

interface LoadInventoryLpnPort {
    List<InventoryWarehouse> findBy(Long aLong, List<Long> list);
}

public class ProceduralLogicTest {
    private LoadWarehousePort loadWarehousePort;
    private CarrierPort carrierPort;
    private LoadInventoryLpnPort loadInventoryLpnPort;

    @Test
    void create() {
        // given
        final Long itemId1 = 1L;
        final Long qty1 = 10L;
        final InventoryTransferOrderItemRequest itemRequest1 = new InventoryTransferOrderItemRequest(itemId1, qty1);

        final Long itemId2 = 2L;
        final Long qty2 = 20L;
        final InventoryTransferOrderItemRequest itemRequest2 = new InventoryTransferOrderItemRequest(itemId2, qty2);

        final Long fromWarehouseId = 1L;
        final Long toWarehouseId = 2L;
        final Long carrierId = 3L;
        final InventoryTransferOrderRequest request = new InventoryTransferOrderRequest(fromWarehouseId, toWarehouseId, Instant.now(), carrierId, "노트", List.of(itemRequest1, itemRequest2));


        // when // todo: application 서비스로 extract
        final List<ItemToTransfer> itemsToTransfer = request.getItemsToCreate();
        final Warehouse toWarehouse = loadWarehousePort.getBy(request.toWarehouseId());
        final Warehouse fromWarehouse = loadWarehousePort.getBy(request.fromWarehouseId());
        final Carrier carrier = carrierPort.getBy(request.carrierId());
        final List<InventoryWarehouse> inventoryWarehouses = loadInventoryLpnPort.findBy(request.fromWarehouseId(), itemsToTransfer.stream().map(ItemToTransfer::itemId).toList());

        //todo: 도메인 서비스로 분리
        final InventoryTransferOrder inventoryTransferOrder = createInventoryTransfer(itemsToTransfer, toWarehouse, fromWarehouse, carrier, inventoryWarehouses);

        // then
        assertThat(inventoryTransferOrder.getFromWarehouseId()).isEqualTo(fromWarehouseId);
        assertThat(inventoryTransferOrder.getToWarehouseId()).isEqualTo(toWarehouseId);
        assertThat(inventoryTransferOrder.getShipping().getShippingItems()).hasSize(2);
    }

    /**
     * 1. 재고 차감
     * 2. 출고 생성 & 출고아이템 생성
     * 3. 출고지시서 생성
     * 4. 대체출고지시서 생성
     */
    private InventoryTransferOrder createInventoryTransfer(final List<ItemToTransfer> itemsToCreate, final Warehouse toWarehouse, final Warehouse fromWarehouse, final Carrier carrier, final List<InventoryWarehouse> inventoryWarehouses) {
        subtractQuantity(itemsToCreate, inventoryWarehouses);
        final Shipping shipping = createShippingToWarehouse(toWarehouse, fromWarehouse, carrier);
        itemsToCreate.forEach(item -> shipping.addShippingItem(item.itemId(), item.qty()));
        inventoryTransferOf(fromWarehouse.getId(), List.of(shipping));
        return new InventoryTransferOrder(fromWarehouse.getId(), toWarehouse.getId(), shipping);
    }

    private void inventoryTransferOf(Long id, List<Shipping> shipping) {
        throw new UnsupportedOperationException("ProceduralLogicTest::inventoryTransferOf not implemented yet");
    }

    private void subtractQuantity(final List<ItemToTransfer> itemsToTransfer, final List<InventoryWarehouse> inventoryWarehouses) {
        itemsToTransfer.forEach(item -> findItem(inventoryWarehouses, item)
                .subtractQuantity(item.qty()));
    }

    private InventoryWarehouse findItem(final List<InventoryWarehouse> inventoryWarehouses, final ItemToTransfer itemToTransfer) {
        return inventoryWarehouses.stream()
                .filter(inventory -> inventory.getItemId().equals(itemToTransfer.itemId()))
                .findFirst()
                .orElseThrow();

    }

    private Shipping createShippingToWarehouse(final Warehouse toWarehouse, final Warehouse fromWarehouse, final Carrier carrier) {
        return new Shipping(1L, null);
    }
}