package pe.msbaek.rfcases.valueobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

// first class collection
public final class Gms {
    private final Map<String, Company> companyMap;

    public Gms(Collection<Company> companies) {
        companyMap = companies.stream()
                .collect(toMap(Company::getCode, company -> company));
    }

    Collection<Company> createOrUpdteLocations(List<LocationRequest> locationRequests) {
        return locationRequests.stream()
                .map(Company.CreateLocationCommand::of)
                .map(this::createOrUpdateLocation)
                .toList();
    }

    private Company createOrUpdateLocation(Company.CreateLocationCommand createLocationCommand) {
        final Company company = companyMap.get(createLocationCommand.companyCode());
        company.createOrUpdateLocation(createLocationCommand);
        return company;
    }
}