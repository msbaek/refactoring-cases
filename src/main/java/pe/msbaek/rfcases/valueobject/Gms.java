package pe.msbaek.rfcases.valueobject;

import java.util.Collection;
import java.util.Objects;

public final class Gms {
    private final Collection<Company> companies;

    public Gms(Collection<Company> companies) {
        this.companies = companies;
    }

    public Collection<Company> companies() {
        return companies;
    }
}