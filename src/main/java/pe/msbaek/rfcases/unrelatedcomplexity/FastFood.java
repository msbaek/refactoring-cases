package pe.msbaek.rfcases.unrelatedcomplexity;

import lombok.RequiredArgsConstructor;

import static pe.msbaek.rfcases.unrelatedcomplexity.FeatureFlags.Feature.PORK_SHAWARMA;

@RequiredArgsConstructor
public class FastFood {
    private final Dependency dependency;
    private final FeatureFlags featureFlags;
    private final Tzatziki tzatziki;

    public void makeShawarma() {
        if (!dependency.isOnionAllowed()) {
            throw new IllegalArgumentException();
        }
        if (featureFlags.isActive(PORK_SHAWARMA)) {
            // stuff
        }
        // complex logic: 7 ifs
    }

    public void makeTzatziki() {
        // complex logic: 5 ifs
        tzatziki.makeTzatziki();
    }
}
