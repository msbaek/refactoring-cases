package pe.msbaek.rfcases.splitabstractions.inventorytransfer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

record CreateInventoryTransferItem(Long goodsId, Long requestedQuantity) { }

class InventoryTransferItem {
    private final Long goodsId;
    private final Long requestedQuantity;

    public InventoryTransferItem(final Long goodsId, final Long requestedQuantity) {
        this.goodsId = goodsId;
        this.requestedQuantity = requestedQuantity;
    }
}

@Getter
class InventoryTransfer {
    private Long id;
    private String serialNumber;
    private final Long fromWarehouseId;
    private final Long toWarehouseId;
    private final LocalDate requestedDate;
    private final String note;
    private final List<InventoryTransferItem> items;

    public InventoryTransfer(final Long fromWarehouseId, final Long toWarehouseId, final LocalDate requestedDate, final String note, final List<InventoryTransferItem> items) {
        this.fromWarehouseId = fromWarehouseId;
        this.toWarehouseId = toWarehouseId;
        this.requestedDate = requestedDate;
        this.note = note;
        this.items = items;
    }
}
class Goods {
}

@Getter
@RequiredArgsConstructor
enum InventoryTransferStatus {
    REQUEST("request", "요청"),
    ACCEPTED("accepted", "요청수략"),
    PROCESSING("processing", "대체출고지시중"),
    COMPLETED("completed", "대체출고지시완료"),
    REJECT("reject", "요청거절");

    private final String code;
    private final String description;

    public String getGroupCode() {
        return getClass().getSimpleName();
    }

    public String getKey() {
        return name();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}


interface LoadWarehousePort {
    List<Warehouse> listOf();

    Optional<Warehouse> findBy(Long id);
}


class Warehouse {
}


class WarehouseNotFoundException extends RuntimeException {
    private final Long warehouseId;

    public WarehouseNotFoundException(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public static WarehouseNotFoundException ofId(Long warehouseId) {
        return new WarehouseNotFoundException(warehouseId);
    }
}
interface InventoryTransferPort {
    List<Goods> listOf(List<Long> goodsIds);
    InventoryTransfer saveWithSerialNumber(InventoryTransfer inventoryTransfer);
}

record Request(Long fromWarehouseId, Long toWarehouseId, LocalDate requestedDate, String note, List<CreateInventoryTransferItem> items){
}

record Response(Long id, String serialNumber) { }

@RequiredArgsConstructor
public class InventoryTransferService {
    private final InventoryTransferPort inventoryTransferPort;
    private final LoadWarehousePort loadWarehousePort;

    public Response create(final Request request) {
        validate(request);
        final InventoryTransfer inventoryTransfer = createInventoryTransfer(request);
        final InventoryTransfer savedInventoryTransfer = inventoryTransferPort.saveWithSerialNumber(inventoryTransfer);
        return new Response(savedInventoryTransfer.getId(), savedInventoryTransfer.getSerialNumber());
    }

    private void validate(final Request request) {
        validateWarehouse(request.fromWarehouseId());
        validateWarehouse(request.toWarehouseId());
        validateGoods(request.items());
    }

    private void validateWarehouse(final Long warehouseId) {
        loadWarehousePort.findBy(warehouseId)
                .orElseThrow(() -> WarehouseNotFoundException.ofId(warehouseId));
    }

    private void validateGoods(final List<CreateInventoryTransferItem> items) {
        final List<Long> goodsIds = getGoodsIds(items);
        for (final Long goodsId : goodsIds) {
            final List<Goods> goods = inventoryTransferPort.listOf(List.of(goodsId));
            if (goods.isEmpty()) throw new IllegalArgumentException("아이템이 존재하지 않습니다. goodsId: " + goodsId);
        }
    }

    private List<Long> getGoodsIds(final List<CreateInventoryTransferItem> items) {
        return items.stream()
                .map(CreateInventoryTransferItem::goodsId)
                .toList();
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