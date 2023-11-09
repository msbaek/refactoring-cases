package pe.msbaek.rfcases.liftupconditionals;

public class Item {
    public String name;
    public int sellIn;
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

    void updateItem() {
        boolean isAgedBrie = name.equals("Aged Brie");
        if (isAgedBrie) {
            if (quality < 50) {
                quality = quality + 1;
            }
            sellIn = sellIn - 1;
            if (sellIn < 0) {
                if (quality < 50) {
                    quality = quality + 1;
                }
            }
        } else {
            if (!name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (quality > 0) {
                    if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                        quality = quality - 1;
                    }
                }
            } else {
                if (quality < 50) {
                    quality = quality + 1;
                    if (sellIn < 11) {
                        if (quality < 50) {
                            quality = quality + 1;
                        }
                    }
                    if (sellIn < 6) {
                        if (quality < 50) {
                            quality = quality + 1;
                        }
                    }
                }
            }
            if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                sellIn = sellIn - 1;
            }
            if (sellIn < 0) {
                if (!name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (quality > 0) {
                        if (!name.equals("Sulfuras, Hand of Ragnaros")) {
                            quality = quality - 1;
                        }
                    }
                } else {
                    quality = 0;
                }
            }
        }
    }
}