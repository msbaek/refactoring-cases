package pe.msbaek.rfcases.refactor_seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FromMostInner {
    private final List<Picking> pickings = new ArrayList<>();
    private PickingGroupType type;
    private PickingGroupStatus status;

    public void reassignLocationOrRemoveErrorPickings(final Bin errorLocation, final Long itemId, final List<InventoryLpn> reassignableInventoryLpns) {
        Assert.state(PickingGroupType.SINGLE == type, "개별집품그룹만 삭제가 가능합니다.");

        // 1. 에러로케이션 상태 변경
        errorLocation.registerItemAsError(itemId);

        // 2. 에러상태의 집품 조회
        final List<Picking> errorPickings = filterPickingBy(errorLocation, itemId);

        // 3-1. 토트에 담긴 수량 시스템 반영
        errorPickings.forEach(Picking::applyToteItemToSystem);

        final List<ReassignQtyByPicking> qtyForReassign = getNotPickedQty(errorLocation, itemId, errorPickings);

        // 3-2. 집품되지 않은 재고 롭
        errorPickings.forEach(errorPicking -> errorPicking.removeErrorLocation(errorLocation, itemId));

        for (final ReassignQtyByPicking reassignQtyByPicking : qtyForReassign) {
            if (shouldReassing(reassignableInventoryLpns, reassignQtyByPicking)) {
                // reassing location
                final Inventories inventories = new InventoriesOfOnlyPickingArea(reassignableInventoryLpns);
                final OrderedItem orderedItem = new OrderedItem(itemId, reassignQtyByPicking.getQuantity());
                final List<PickingLocation> createdPickingLocations = new PickingLocationFactory().createPickingLocations(inventories, List.of(orderedItem));
                reassignQtyByPicking.getPicking().addPickingLocations(createdPickingLocations);
            } else {
                // reset shipping
                reassignQtyByPicking.getPicking().clear();
                removePickingGroupIfPickingEmpty();
                updateStatusToComplete();
            }
        }
    }

    private boolean shouldReassing(final List<InventoryLpn> reassignableInventoryLpns, final ReassignQtyByPicking reassignQtyByPicking) {
        final Long remainingQty = reassignQtyByPicking.getQuantity();

        final long reassignableQty = reassignableInventoryLpns.stream()
                .filter(inventoryByLocation -> 0L < inventoryByLocation.getQty())
                .mapToLong(InventoryLpn::getQty)
                .sum();

        return reassignableQty >= remainingQty;
    }

    private List<Picking> filterPickingBy(Bin location, Long itemId) {
        return pickings.stream()
                .filter(picking -> picking.hasLocationAndItem(location, itemId))
                .collect(Collectors.toList());
    }

    private List<ReassignQtyByPicking> getNotPickedQty(Bin errorLocation, Long itemId, List<Picking> errorPickings) {
        return errorPickings.stream()
                .map(picking -> new ReassignQtyByPicking(picking, picking.getNotPickedQty(errorLocation, itemId)))
                .collect(Collectors.toList());
    }

    private void removePickingGroupIfPickingEmpty() {
        if (pickings.isEmpty()) {
            // 구현: 집품그룹 삭제 로직
        }
    }

    private void updateStatusToComplete() {
        this.status = PickingGroupStatus.COMPLETED;
    }

    // Inner classes and interfaces
    public static class Assert {
        public static void state(boolean condition, String message) {
            if (!condition) {
                throw new IllegalStateException(message);
            }
        }
    }

    public enum PickingGroupType {
        SINGLE, BATCH
    }

    public enum PickingGroupStatus {
        WAITING, IN_PROGRESS, COMPLETED, CANCELLED
    }

    @FunctionalInterface
    interface InventoryFilter {
        boolean filter(InventoryLpn inventory);
    }

    record OrderedItem(Long itemId, Long quantity) {}

    record ReassignQtyByPicking(Picking picking, Long quantity) {
        public Picking getPicking() {
            return picking;
        }

        public Long getQuantity() {
            return quantity;
        }
    }

    class Picking {
        private List<PickingLocation> pickingLocations = new ArrayList<>();
        private boolean isCleared = false;

        public void clear() {
            isCleared = true;
            pickingLocations.clear();
        }

        public void applyToteItemToSystem() {
            // 구현: 토트에 담긴 수량 시스템 반영 로직
        }

        public boolean hasLocationAndItem(Bin location, Long itemId) {
            return pickingLocations.stream()
                    .anyMatch(pl -> pl.matches(location, itemId));
        }

        public Long getNotPickedQty(Bin location, Long itemId) {
            return pickingLocations.stream()
                    .filter(pl -> pl.matches(location, itemId))
                    .mapToLong(PickingLocation::getNotPickedQuantity)
                    .sum();
        }

        public void removeErrorLocation(Bin location, Long itemId) {
            pickingLocations.removeIf(pl -> pl.matches(location, itemId));
        }

        public void addPickingLocations(List<PickingLocation> locations) {
            this.pickingLocations.addAll(locations);
        }
    }

    class PickingLocation {
        private final Bin location;
        private final Long itemId;
        private Long notPickedQuantity;

        public PickingLocation(Bin location, Long itemId, Long quantity) {
            this.location = location;
            this.itemId = itemId;
            this.notPickedQuantity = quantity;
        }

        public boolean matches(Bin location, Long itemId) {
            return Objects.equals(this.location, location) && Objects.equals(this.itemId, itemId);
        }

        public Long getNotPickedQuantity() {
            return notPickedQuantity;
        }
    }

    class Bin {
        private List<Long> errorItems = new ArrayList<>();

        public void registerItemAsError(Long itemId) {
            errorItems.add(itemId);
        }
    }

    class InventoryLpn {
        private Long qty;
        private Bin bin;

        public Long getQty() {
            return qty;
        }
    }

    interface Inventories {
        List<InventoryLpn> getInventories();
    }

    class InventoriesOfOnlyPickingArea implements Inventories {
        private final List<InventoryLpn> inventories;

        public InventoriesOfOnlyPickingArea(List<InventoryLpn> inventories) {
            this.inventories = inventories;
        }

        @Override
        public List<InventoryLpn> getInventories() {
            return inventories;
        }
    }

    class PickingLocationFactory {
        public List<PickingLocation> createPickingLocations(Inventories inventories, List<OrderedItem> orderedItems) {
            List<PickingLocation> result = new ArrayList<>();
            // 구현: 집품 로케이션 생성 로직
            return result;
        }
    }
}