package pe.msbaek.rfcases.spitphase;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

record PathCountResponse(Long id, Long warehouseId, String conveyorPath, Long count) { }
record ConveyorRegistry(List<Conveyor> conveyors) {
    public Conveyor getOrDefaultConveyoer(Shipping shipping, List<HmItem> hmItems) {
        return new Conveyor(1L, 1L, new ConveyorPath(1l, "path"));
    }
}
record ConveyorPath(Long id, String description) {}
record Conveyor(Long id, Long warehouseId, ConveyorPath conveyorPath) { }
record PackingStatus(String description) { }
record HmItem(Long itemId, Long warehouseId) { }
record DeliveryType(String name) {}
record Carrier(DeliveryType deliveryType) {}
record Shipping(Long id, Long warehouseId, Long totalQty, Long estimatedWeight, Long estimatedBoxId, Carrier carrier, Boolean isAvailableDelivery, Set<Long> itemIds) { }

interface ConveyorPort {
    List<Conveyor> listConveyor();
    List<HmItem> listHmItem(Set<Long> shippingItemIds);
}

interface ShippingPort {
    List<Shipping> listOf(Long warehouseId);
}

@RequiredArgsConstructor
public class ConveyorService {
    private final ConveyorPort conveyorPort;
    private final ShippingPort shippingPort;

    public List<PathCountResponse> getCountByConveyorPath(final Long warehouseId) {
        List<Conveyor> conveyors = conveyorPort.listConveyor();
        final ConveyorRegistry conveyorRegistry = new ConveyorRegistry(conveyors);
        final List<Shipping> shippings = shippingPort.listOf(warehouseId);
        final Set<Long> shippingItemIds = getShippingItemIds(shippings);
        final List<HmItem> hmItemList = conveyorPort.listHmItem(shippingItemIds);

        final Map<Long, PathCountResponse> pathCountMap = createInitResponse(conveyors);

        for (final Shipping shipping : shippings) {
            final Set<Long> itemIds = shipping.itemIds();
            final List<HmItem> hmItems = filterItemsByShipping(hmItemList, itemIds);
            final Conveyor conveyor = conveyorRegistry.getOrDefaultConveyoer(shipping, hmItems);
            countingByPaths(pathCountMap, conveyor);
        }

        return new ArrayList<>(pathCountMap.values());
    }

    private Set<Long> getShippingItemIds(final List<Shipping> shippings) {
        return shippings.stream()
                .map(Shipping::itemIds)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Map<Long, PathCountResponse> createInitResponse(List<Conveyor> conveyors) {
        Map<Long, PathCountResponse> pathCountMap = new HashMap<>();
        for (Conveyor conveyor : conveyors) {
            pathCountMap.put(conveyor.id(),
                    new PathCountResponse(
                            conveyor.id(),
                            conveyor.warehouseId(),
                            conveyor.conveyorPath().description(),
                            0L));
        }
        return pathCountMap;
    }

    private List<HmItem> filterItemsByShipping(final List<HmItem> hmItemList, final Set<Long> itemIds) {
        return hmItemList.stream()
                .filter(hmItem -> itemIds.contains(hmItem.itemId()))
                .collect(Collectors.toList());
    }

    private void countingByPaths(final Map<Long, PathCountResponse> pathCountMap, final Conveyor conveyor) {
        pathCountMap.computeIfPresent(conveyor.id(),
                (id, response) -> new PathCountResponse(
                        response.id(),
                        response.warehouseId(),
                        response.conveyorPath(),
                        response.count() + 1));
    }
}