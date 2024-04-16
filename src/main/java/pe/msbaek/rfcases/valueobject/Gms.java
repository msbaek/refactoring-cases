package pe.msbaek.rfcases.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

// first class collection
public final class Gms {
    private final Map<String, Company> companyMap;

    public Gms(Collection<Company> companies) {
        companyMap = companies.stream()
                .collect(toMap(Company::getCode, company -> company));
    }

    void createOrUpdteLocations(List<LocationRequest> locationRequests) {
        for (LocationRequest locationRequest : locationRequests) {
            Company.CreateLocationCommand createLocationCommand = Company.CreateLocationCommand.of(locationRequest);
            // final Company company = getCompany(companies, createLocationCommand.companyCode());
            final Company company = companyMap.get(createLocationCommand.companyCode());
            company.createOrUpdateLocation(createLocationCommand);
        }
    }
}