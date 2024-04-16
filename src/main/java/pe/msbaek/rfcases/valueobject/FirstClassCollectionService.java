package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class FirstClassCollectionService {
    private CompanyPort companyPort;

    public void createLocation(final List<LocationRequest> locationRequests) {
        final Collection<Company> companies = companyPort.loadAll();
        createOrUpdteLocations(locationRequests, new Gms(companies));

        companyPort.saveAll(companies);
    }

    private void createOrUpdteLocations(List<LocationRequest> locationRequests, Gms gms) {
        Map<String, Company> companyMap = gms.companies().stream()
                .collect(toMap(Company::getCode, company -> company));

        for (LocationRequest locationRequest : locationRequests) {
            Company.CreateLocationCommand createLocationCommand = mapToCreateLocationCommand(locationRequest);
            // final Company company = getCompany(companies, createLocationCommand.companyCode());
            final Company company = companyMap.get(createLocationCommand.companyCode());
            company.createOrUpdateLocation(createLocationCommand);
        }
    }

    private Company.CreateLocationCommand mapToCreateLocationCommand(final LocationRequest locationRequest) {
        return Company.CreateLocationCommand.of(locationRequest);
    }
}

record LocationRequest() {

}

@Getter
class Company {

    private String code;

    public void createOrUpdateLocation(CreateLocationCommand createLocationCommand) {
        throw new UnsupportedOperationException("Company::createOrUpdateLocation not implemented yet");
    }
    record CreateLocationCommand(String companyCode) {
        public static CreateLocationCommand of(LocationRequest locationRequest) {
            throw new UnsupportedOperationException("CreateLocationCommand::of not implemented yet");
        }
    }

}

interface CompanyPort {

    Collection<Company> loadAll();
    void saveAll(Collection<Company> companies);
}