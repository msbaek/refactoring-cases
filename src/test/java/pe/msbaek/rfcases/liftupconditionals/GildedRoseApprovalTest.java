package pe.msbaek.rfcases.liftupconditionals;

import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@UseReporter(DiffReporter.class)
public class GildedRoseApprovalTest {
    @Test
    public void foo() {
        String [] name = {"foo", "Aged Brie", "Backstage passes to a TAFKAL80ETC concert", "Sulfuras, Hand of Ragnaros"};
        Integer [] sellIn = {-1, 0, 11};
        Integer [] quality = {0, 1, 49, 50};

        CombinationApprovals.verifyAllCombinations(this::getItems, name, sellIn, quality);
    }

    private String getItems(String name, int sellIn, int quality) {
        GildedRose app = new GildedRose(new Item[]{
                new Item(name, sellIn, quality)
        });
        app.updateQuality();
        return Arrays.asList(app.items).toString();
    }
}