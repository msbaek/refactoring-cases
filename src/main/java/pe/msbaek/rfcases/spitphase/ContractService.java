package pe.msbaek.rfcases.spitphase;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ExcelExporter excelExporter;
    @Value("${warning.threshold}") // from application.properties
    public Double warningThreshold;

    private boolean hasWarning(Contract contract) {
        return contract.getStatus() == Contract.Status.ACTIVE
                && contract.getLastPayment().isBefore(now().minusDays(60))
                && contract.getRemainingValue() > warningThreshold;
    }

    record ContractForExport(String number, String name, boolean hasWarning) {}

    private ContractForExport toContractForExport(Contract c) {
        return new ContractForExport(c.getNumber(), c.getName(), hasWarning(c));
    }

    public void exportContracts(List<Contract> contracts) {
        List<ContractForExport> contractForExports = contracts.stream()
                .map(c -> toContractForExport(c))
                .toList();

        excelExporter.exportExcel(contractForExports);
    }
}