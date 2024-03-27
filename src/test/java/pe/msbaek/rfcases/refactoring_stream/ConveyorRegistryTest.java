package pe.msbaek.rfcases.refactoring_stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemOptionBuilder {
    private Long id;
    private Long weight;

    public ItemOptionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ItemOptionBuilder weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public ItemOption build() {
        return new ItemOption() {
            public Long getId() {
                return id;
            }

            public Long getWeight() {
                return weight;
            }
        };
    }
}

class ShippingItemBuilder {
    private Long itemId;
    private Long qty;

    public ShippingItemBuilder itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public ShippingItemBuilder qty(Long qty) {
        this.qty = qty;
        return this;
    }

    public ShippingItem build() {
        return new ShippingItem() {
            public Long getItemId() {
                return itemId;
            }

            public Long getQty() {
                return qty;
            }
        };
    }
}

class ConveyorRegistryTest {
    @Test
    public void testCalculateTotalWeight() {
        ConveyorRegistry conveyorRegistry = new ConveyorRegistry();
        List<ItemOption> itemOptions = Arrays.asList(
                anItemOption().id(1L).weight(2L).build(),
                anItemOption().id(2L).weight(3L).build()
        );

        List<ShippingItem> shippingItems = Arrays.asList(
                aShippingItem().itemId(1L).qty(2L).build(),
                aShippingItem().itemId(2L).qty(3L).build()
        );
        Long expectedTotalWeight = 2L * 2L + 3L * 3L;

        Long actualTotalWeight = conveyorRegistry.calculateTotalWeight(itemOptions, shippingItems);

        assertEquals(expectedTotalWeight, actualTotalWeight);
    }

    private ShippingItemBuilder aShippingItem() {
        return new ShippingItemBuilder();
    }

    private ItemOptionBuilder anItemOption() {
        return new ItemOptionBuilder();
    }
}