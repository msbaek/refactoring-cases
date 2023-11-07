package pe.msbaek.rfcases.kt4u;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public abstract class ExcelDownload<T> {
    public Workbook from(final List<T> parcelPostList) {
        final List<String[]> data = createExcelData(parcelPostList);
        return createWorkbook(data);
    }

    private List<String[]> createExcelData(final List<T> packingList) {
        final List<String[]> data = new ArrayList<>();
        data.add(header());

        for (final T packing : packingList) {
            data.add(row(packing));
        }
        return data;
    }

    private Workbook createWorkbook(final List<String[]> data) {
        final Workbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet(getSheetName());

        int rowCount = 0;
        for (final String[] rowData : data) {
            final Row row = sheet.createRow(rowCount);
            rowCount++;

            int columnCount = 0;
            for (final String columnData : rowData) {
                final Cell cell = row.createCell(columnCount);
                columnCount++;
                cell.setCellValue(columnData);
            }
        }
        return workbook;
    }

    protected abstract String getSheetName();

    protected abstract String[] header();

    protected abstract String[] row(T packing);
}