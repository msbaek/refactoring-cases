package pe.msbaek.rfcases.unrelatedcomplexity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static pe.msbaek.rfcases.unrelatedcomplexity.FeatureFlags.Feature.PORK_SHAWARMA;

@ExtendWith(MockitoExtension.class)
class FastFoodTest {
    @Mock
    Dependency dependency;
    @Mock
    FeatureFlags featureFlags;
    @InjectMocks
    FastFood fastFood;

    @BeforeEach
    final void before() {
        when(dependency.isOnionAllowed()).thenReturn(true);
        when(featureFlags.isActive(PORK_SHAWARMA)).thenReturn(true);
        when(dependency.isCucumberAllowed()).thenReturn(true);
    }

    @Test
    void shawarmaTest() { // + 7 more tests
        // ... complex
        fastFood.makeShawarma();
    }

    @Test
    void shawarmaTest1() { // + 7 more tests
        // ... complex
        fastFood.makeShawarma();
    }

    @Test
    void tzatzikiTest() { // + 5 more tests
        // ... complex
        fastFood.makeTzatziki();
    }

    @Test
    void tzatzikiTest1() { // + 5 more tests
        // ... complex
        fastFood.makeTzatziki();
    }
}