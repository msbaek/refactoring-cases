package pe.msbaek.rfcases.spitphase.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractExporter excelExporter;
    @Value("${warning.threshold}") // from application.properties
    public Double warningThreshold;

    public record ContractForExport(String number, String name, boolean hasWarning) {}

    public void exportContracts(List<Contract> contracts) {
        List<ContractForExport> contractForExports = contracts.stream()
                .map(c -> new ContractForExport(c.getNumber(), c.getName(), c.hasWarning(this.warningThreshold)))
                .toList();

        excelExporter.exportExcel(contractForExports);
    }
}