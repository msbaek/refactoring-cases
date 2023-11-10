package pe.msbaek.rfcases.unrelatedcomplexity;

public interface FeatureFlags {
    enum Feature {
        PORK_SHAWARMA
    }
    boolean isActive(Feature feature);
}
