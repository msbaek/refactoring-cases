package pe.msbaek.rfcases.spitphase.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.msbaek.rfcases.spitphase.domain.ContractService.ContractForExport;
import pe.msbaek.rfcases.spitphase.infra.ExcelExporter;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static pe.msbaek.rfcases.spitphase.domain.Contract.Status.ACTIVE;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {
    private static final double WARNING_AMOUNT_THRESHOLD = 10_000;
    private static final double AMOUNT_OVER_WARNING_THRESHOLD = 10_001d;
    @Mock private ExcelExporter excelExporter;
    @InjectMocks private ContractService sut;
    @Captor private ArgumentCaptor<List<ContractForExport>> argCaptor;

    @Test
    void warning_overduePayments() {
        Contract contract = new Contract();
        contract.setStatus(ACTIVE);
        contract.setLastPayment(now().minusDays(59));
        contract.setRemainingValue(AMOUNT_OVER_WARNING_THRESHOLD);

        sut.exportContracts(List.of(contract));

        // 이제 엑셀과 무관하게 테스트 가능
        verify(excelExporter).exportExcel(argCaptor.capture());
        ContractForExport contractForExport = argCaptor.getValue().get(0);
        assertThat(contractForExport.toString()).isEqualTo("ContractForExport[number=null, name=null, hasWarning=false]");
    }
}