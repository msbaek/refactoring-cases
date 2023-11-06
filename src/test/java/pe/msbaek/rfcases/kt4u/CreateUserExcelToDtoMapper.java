package pe.msbaek.rfcases.kt4u;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StringUtils;

public class CreateUserExcelToDtoMapper extends ExcelToDtoMapper<CreateUserRequest> {
    public CreateUserExcelToDtoMapper() {
    }

    @Override
    CreateUserRequest toDto(Sheet worksheet, int rowIndex) {
        final Row row = worksheet.getRow(rowIndex);
        final String loginId = row.getCell(0).toString();
        final String passwd = row.getCell(1).toString();
        final String username = row.getCell(2).toString();
        final String rowBoLoginId = null != row.getCell(3) ? row.getCell(3).toString() : null;
        final String boLoginId = StringUtils.hasText(rowBoLoginId) ? rowBoLoginId : null;
        return new CreateUserRequest(loginId, passwd, username, boLoginId);
    }
}