package pe.msbaek.rfcases.refactoring_stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConveyorRegistryTest {
    @Test
    public void testCalculateTotalWeight() {
        ConveyorRegistry conveyorRegistry = new ConveyorRegistry();

        List<ItemOption> itemOptions = Arrays.asList(
                new ItemOption() {
                    public Long getId() { return 1L; }
                    public Long getWeight() { return 2L; }
                },
                new ItemOption() {
                    public Long getId() { return 2L; }
                    public Long getWeight() { return 3L; }
                }
        );

        List<ShippingItem> shippingItems = Arrays.asList(
                new ShippingItem() {
                    public Long getItemId() { return 1L; }
                    public Long getQty() { return 2L; }
                },
                new ShippingItem() {
                    public Long getItemId() { return 2L; }
                    public Long getQty() { return 3L; }
                }
        );

        Long expectedTotalWeight = 2L*2L + 3L*3L;
        Long actualTotalWeight = conveyorRegistry.calculateTotalWeight(itemOptions, shippingItems);

        assertEquals(expectedTotalWeight, actualTotalWeight);
    }
}