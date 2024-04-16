package pe.msbaek.rfcases.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Gms {
    public Gms() {
    }

    Collection<Company> createOrUpdateLocations(List<LocationRequest> locationRequests, Collection<Company> companies) {
        Map<String, Company> companyMap = companies.stream()
                .collect(Collectors.toMap(Company::getCode, company -> company));

        Collection<Company> updateCompanies = locationRequests.stream()
                .map(this::mapToCreateLocationCommand)
                .map(createLocationCommand -> createOrUpdateLocation(companyMap, createLocationCommand))
                .toList();
        return updateCompanies;
    }

    Company createOrUpdateLocation(Map<String, Company> companyMap, Company.CreateLocationCommand createLocationCommand) {
        final Company company = companyMap.get(createLocationCommand.companyCode());
        company.createOrUpdateLocation(createLocationCommand);
        return company;
    }

    Company.CreateLocationCommand mapToCreateLocationCommand(final LocationRequest locationRequest) {
        return Company.CreateLocationCommand.of(locationRequest);
    }
}