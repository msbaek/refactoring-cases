package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class FirstClassCollectionService {
    private final Gms gms = new Gms();
    private CompanyPort companyPort;

    public void createLocation(final List<LocationRequest> locationRequests) {
        final Collection<Company> companies = companyPort.loadAll();
        Collection<Company> updateCompanies = gms.createOrUpdateLocations(locationRequests, companies);

        companyPort.saveAll(updateCompanies);
    }

    private Collection<Company> createOrUpdateLocations(List<LocationRequest> locationRequests, Collection<Company> companies) {

        /**
         * forEach르 없애고 싶다.
         * Collection<Company>, Collection<CreateLocationCommand>를 가지고 뭔가를 한다
         * Collection<Company>를 first class collection으로 만들어야 함
         */
        return gms.createOrUpdateLocations(locationRequests, companies);
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