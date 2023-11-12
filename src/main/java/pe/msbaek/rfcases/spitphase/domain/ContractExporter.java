package pe.msbaek.rfcases.spitphase.domain;

import pe.msbaek.rfcases.spitphase.domain.ContractService;

import java.util.List;

public interface ContractExporter {
    void exportExcel(List<ContractService.ContractForExport> contractForExports)// to allow @Spy from unit tests
    ;
}
