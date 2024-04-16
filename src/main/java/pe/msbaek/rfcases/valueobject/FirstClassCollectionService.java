package pe.msbaek.rfcases.valueobject;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class FirstClassCollectionService {
    private CompanyPort companyPort;

    public void createLocation(final List<LocationRequest> locationRequests) {
        final Collection<Company> companies = companyPort.loadAll();
        Map<String, Company> companyMap = companyPort.loadAll().stream()
                .collect(toMap(Company::getCode, company -> company));

        /**
         * forEach르 없애고 싶다.
         * Collection<Company>, Collection<CreateLocationCommand>를 가지고 뭔가를 한다
         * Collection<Company>를 first class collection으로 만들어야 함
         */
        Collection<Company> updateCompanies = locationRequests.stream()
                .map(this::mapToCreateLocationCommand)
                .map(createLocationCommand -> createOrUpdateLocation(companyMap, createLocationCommand))
                .toList();

        companyPort.saveAll(updateCompanies);
    }

    private Company createOrUpdateLocation(Map<String, Company> companyMap, Company.CreateLocationCommand createLocationCommand) {
        final Company company = companyMap.get(createLocationCommand.companyCode());
        company.createOrUpdateLocation(createLocationCommand);
        return company;
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