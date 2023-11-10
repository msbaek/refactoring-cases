package pe.msbaek.rfcases.unrelatedcomplexity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Tzatziki {
    private final Dependency dependency;

    public void makeTzatziki() {
        if (!dependency.isCucumberAllowed()) {
            throw new IllegalArgumentException();
        }
        // complex logic: 5 ifs
    }
}