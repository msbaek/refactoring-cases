package pe.msbaek.rfcases.kt4u;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        final List<CreateUserRequest> createUserRequests = read(file);
        final List<CreateUserRequest> results = filterInvalidCredentials(createUserRequests);
        userUseCase.createUsers(results);
        return ResponseEntity.ok().build();
    }

    private List<CreateUserRequest> filterInvalidCredentials(List<CreateUserRequest> createUserRequests) {
        final List<CreateUserRequest> results = new ArrayList<>();
        for (CreateUserRequest createUserRequest : createUserRequests) {
            // Check if the row is empty
            if (isCredentialsValid(createUserRequest.loginId(), createUserRequest.password(), createUserRequest.username())) {
                results.add(createUserRequest);
            }
        }
        return results;
    }

    private boolean isCredentialsValid(final String cellLoginId, final String cellPasswd, final String cellUsername) {
        return StringUtils.hasText(cellLoginId) && StringUtils.hasText(cellPasswd) && StringUtils.hasText(cellUsername);
    }

    private List<CreateUserRequest> read(MultipartFile file) {
        final Sheet worksheet = getSheet(file);
        final List<CreateUserRequest> createUserRequests = new ArrayList<>();
        final int lastRowNum = worksheet.getLastRowNum();
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            CreateUserRequest result = toDto(worksheet, rowIndex);
            createUserRequests.add(result);
        }
        return createUserRequests;
    }

    private Sheet getSheet(MultipartFile file) {
        Workbook workbook = createWorkbook(file);
        return workbook.getSheetAt(0);
    }

    private Workbook createWorkbook(MultipartFile file) {
        final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"xlsx".equals(extension) && !"xls".equals(extension)) throw new RuntimeException("엑셀파일만 업로드 해주세요.");
        Workbook workbook = null;
        if ("xlsx".equals(extension)) {
            try {
                workbook = new XSSFWorkbook(file.getInputStream());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        } else if ("xls".equals(extension)) {
            try {
                workbook = new HSSFWorkbook(file.getInputStream());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return workbook;
    }

    private CreateUserRequest toDto(Sheet worksheet, int rowIndex) {
        final Row row = worksheet.getRow(rowIndex);
        final String loginId = row.getCell(0).toString();
        final String passwd = row.getCell(1).toString();
        final String username = row.getCell(2).toString();
        final String rowBoLoginId = null != row.getCell(3) ? row.getCell(3).toString() : null;
        final String boLoginId = StringUtils.hasText(rowBoLoginId) ? rowBoLoginId : null;
        return new CreateUserRequest(loginId, passwd, username, boLoginId);
    }

    private record Result(String loginId, String passwd, String username, String boLoginId) {
    }
}