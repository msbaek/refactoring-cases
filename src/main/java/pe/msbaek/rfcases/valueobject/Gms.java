package pe.msbaek.rfcases.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public final class Gms {
    private final Collection<Company> companies;

    public Gms(Collection<Company> companies) {
        this.companies = companies;
    }

    public Collection<Company> companies() {
        return companies;
    }

    void createOrUpdteLocations(List<LocationRequest> locationRequests) {
        Map<String, Company> companyMap = companies().stream()
                .collect(toMap(Company::getCode, company -> company));

        for (LocationRequest locationRequest : locationRequests) {
            Company.CreateLocationCommand createLocationCommand = Company.CreateLocationCommand.of(locationRequest);
            // final Company company = getCompany(companies, createLocationCommand.companyCode());
            final Company company = companyMap.get(createLocationCommand.companyCode());
            company.createOrUpdateLocation(createLocationCommand);
        }
    }
}