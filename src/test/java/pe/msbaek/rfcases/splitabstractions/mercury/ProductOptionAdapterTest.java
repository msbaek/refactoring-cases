package pe.msbaek.rfcases.splitabstractions.mercury;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductOptionAdapterTest {
    @Test
    void name() {
        new ProductOptionAdapter(null, null, null).createProductOption(null);
    }
}