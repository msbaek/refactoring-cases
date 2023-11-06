package pe.msbaek.rfcases.kt4u;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ExcelToDtoMapper {
    List<CreateUserRequest> read(MultipartFile file) {
        Workbook workbook = createWorkbook(file);
        final Sheet worksheet = workbook.getSheetAt(0);
        final List<CreateUserRequest> createUserRequests = new ArrayList<CreateUserRequest>();
        final int lastRowNum = worksheet.getLastRowNum();
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            CreateUserRequest result = toDto(worksheet, rowIndex);
            createUserRequests.add(result);
        }
        return createUserRequests;
    }

    Workbook createWorkbook(MultipartFile file) {
        final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"xlsx".equals(extension) && !"xls".equals(extension)) throw new RuntimeException("엑셀파일만 업로드 해주세요.");
        Workbook workbook = null;
        if ("xlsx".equals(extension)) {
            try {
                workbook = new XSSFWorkbook(file.getInputStream());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                workbook = new HSSFWorkbook(file.getInputStream());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return workbook;
    }

    abstract CreateUserRequest toDto(Sheet worksheet, int rowIndex);
}
