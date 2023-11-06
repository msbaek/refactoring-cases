package pe.msbaek.rfcases.kt4u;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/excel-upload",
            consumes = {"multipart/form-data"}
    )
    public ResponseEntity<Void> createUsersFromExcel(final MultipartFile file) {
        final Workbook workbook = ExcelUtils.createWorkbook(file);
        final Sheet worksheet = workbook.getSheetAt(0);
        final List<CreateUserRequest> createUserRequests = createUserRequest(worksheet);
        userUseCase.createUsers(createUserRequests);
        return ResponseEntity.ok().build();
    }

    private List<CreateUserRequest> createUserRequest(final Sheet worksheet) {
        final List<CreateUserRequest> createUserRequests = new ArrayList<>();
        final int lastRowNum = worksheet.getLastRowNum();
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            final Row row = worksheet.getRow(rowIndex);

            final String loginId = row.getCell(0).toString();
            final String passwd = row.getCell(1).toString();
            final String username = row.getCell(2).toString();
            final String rowBoLoginId = null != row.getCell(3) ? row.getCell(3).toString() : null;
            final String boLoginId = StringUtils.hasText(rowBoLoginId) ? rowBoLoginId : null;

            // Check if the row is empty
            if (isCredentialsValid(loginId, passwd, username)) {
                createUserRequests.add(new CreateUserRequest(loginId, passwd, username, boLoginId));
            }
        }
        return createUserRequests;
    }

    private boolean isCredentialsValid(final String cellLoginId, final String cellPasswd, final String cellUsername) {
        return StringUtils.hasText(cellLoginId) && StringUtils.hasText(cellPasswd) && StringUtils.hasText(cellUsername);
    }
}