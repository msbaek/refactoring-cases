package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toMap;

public class FirstClassCollectionService {
    private CompanyPort companyPort;

    public void createLocation(final List<LocationRequest> locationRequests) {
        final Collection<Company> companies = companyPort.loadAll();

        Collection<Company> updatedCompanies = new Gms(companies).createOrUpdteLocations(locationRequests);

        companyPort.saveAll(updatedCompanies);
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