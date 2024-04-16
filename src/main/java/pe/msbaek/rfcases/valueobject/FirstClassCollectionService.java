package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

public class FirstClassCollectionService {
    private CompanyPort companyPort;

    public void createLocation(final List<LocationRequest> locationRequests) {
        final Collection<Company> companies = companyPort.loadAll();

        for (LocationRequest locationRequest : locationRequests) {
            Company.CreateLocationCommand createLocationCommand = mapToCreateLocationCommand(locationRequest);
            final Company company = getCompany(companies, createLocationCommand.companyCode());
            company.createOrUpdateLocation(createLocationCommand);
        }

        companyPort.saveAll(companies);
    }

    private Company.CreateLocationCommand mapToCreateLocationCommand(final LocationRequest locationRequest) {
        return Company.CreateLocationCommand.of(locationRequest);
    }

    private Company getCompany(final Collection<Company> companies, final String companyCode) {
        return companies.stream()
                .filter(company1 -> company1.getCode().equals(companyCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found"));
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