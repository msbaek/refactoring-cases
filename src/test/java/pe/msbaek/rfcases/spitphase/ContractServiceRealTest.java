package pe.msbaek.rfcases.spitphase;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.msbaek.rfcases.spitphase.Contract.Status;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static pe.msbaek.rfcases.spitphase.Contract.Status.*;

public class ContractServiceRealTest {
    private static final double WARNING_AMOUNT_THRESHOLD = 10_000;
    private static final double AMOUNT_OVER_WARNING_THRESHOLD = 10_001d;
    ContractService sut = new ContractService();

    @BeforeEach
    final void before() {
        sut.warningThreshold = WARNING_AMOUNT_THRESHOLD;
    }

    private XSSFRow getContractRow() {
        try (Workbook wb = WorkbookFactory.create(new FileInputStream("output.xlsx"))) {
            XSSFWorkbook workbook = (XSSFWorkbook) wb;
            XSSFSheet sheet = workbook.getSheet("Contracts");
            return sheet.getRow(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void normalContents() {
        Contract contract = new Contract();
        contract.setName("name");
        contract.setNumber("number");

        sut.exportContracts(List.of(contract));

        XSSFRow contractRow = getContractRow();
        assertThat(contractRow.getCell(0).getStringCellValue()).isEqualTo("number");
        assertThat(contractRow.getCell(1).getStringCellValue()).isEqualTo("name");
    }

    @Test
    void warning_overduePayments() {
        Contract contract = new Contract();
        contract.setStatus(ACTIVE);
        contract.setLastPayment(now().minusDays(80));
        contract.setRemainingValue(AMOUNT_OVER_WARNING_THRESHOLD);

        sut.exportContracts(List.of(contract));

        assertThat(getContractRow().getCell(0)
                .getCellStyle().getFillForegroundColor())
                .isEqualTo(IndexedColors.LIGHT_ORANGE.getIndex());
    }

    @Test
    void noWarning_paymentsLessThan60DaysAgo() throws IOException {
        Contract contract = new Contract();
        contract.setStatus(ACTIVE);
        contract.setLastPayment(now().minusDays(59));
        contract.setRemainingValue(AMOUNT_OVER_WARNING_THRESHOLD);

        sut.exportContracts(List.of(contract));

        assertThat(getContractRow().getCell(0)
                .getCellStyle().getFillForegroundColor())
                .isNotEqualTo(IndexedColors.LIGHT_ORANGE.getIndex());
    }

    @Test
    void noWarning_forDraftContracts() throws IOException {
        Contract contract = new Contract();
        contract.setStatus(Status.CLOSED);
        contract.setLastPayment(now().minusDays(80));
        contract.setRemainingValue(AMOUNT_OVER_WARNING_THRESHOLD);

        sut.exportContracts(List.of(contract));

        assertThat(getContractRow().getCell(0)
                .getCellStyle().getFillForegroundColor())
                .isNotEqualTo(IndexedColors.LIGHT_ORANGE.getIndex());
    }
}