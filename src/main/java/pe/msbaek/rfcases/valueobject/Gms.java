package pe.msbaek.rfcases.valueobject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Gms {

    private final Map<String, Company> companyMap;

    public Gms(Collection<Company> companies) {
        companyMap = companies.stream()
                .collect(Collectors.toMap(Company::getCode, company -> company));
    }

    Collection<Company> createOrUpdateLocations(List<LocationRequest> locationRequests) {
        Collection<Company> updateCompanies = locationRequests.stream()
                .map(this::mapToCreateLocationCommand)
                .map(createLocationCommand -> createOrUpdateLocation(createLocationCommand))
                .toList();
        return updateCompanies;
    }

    private Company createOrUpdateLocation(Company.CreateLocationCommand createLocationCommand) {
        final Company company = this.companyMap.get(createLocationCommand.companyCode());
        company.createOrUpdateLocation(createLocationCommand);
        return company;
    }

    private Company.CreateLocationCommand mapToCreateLocationCommand(final LocationRequest locationRequest) {
        return Company.CreateLocationCommand.of(locationRequest);
    }
}