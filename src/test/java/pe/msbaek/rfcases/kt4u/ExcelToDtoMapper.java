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

public abstract class ExcelToDtoMapper<T> {
    List<T> read(MultipartFile file) {
        Workbook workbook = createWorkbook(file);
        final Sheet worksheet = workbook.getSheetAt(0);

        final List<T> createUserRequests = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= worksheet.getLastRowNum(); rowIndex++) {
            createUserRequests.add(toDto(worksheet, rowIndex));
        }
        return createUserRequests;
    }

    Workbook createWorkbook(MultipartFile file) {
        final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!isXlsx(extension) && !isXls().equals(extension))
            throw new RuntimeException("엑셀파일만 업로드 해주세요.");

        try {
            return createWorkBookInternally(file, extension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Workbook createWorkBookInternally(MultipartFile file, String extension) throws IOException {
        return isXlsx(extension) ?
                new XSSFWorkbook(file.getInputStream()) :
                new HSSFWorkbook(file.getInputStream());
    }

    private String isXls() {
        return "xls";
    }

    private boolean isXlsx(String extension) {
        return "xlsx".equals(extension);
    }

    abstract T toDto(Sheet worksheet, int rowIndex);
}