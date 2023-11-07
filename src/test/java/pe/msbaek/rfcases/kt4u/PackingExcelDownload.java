package pe.msbaek.rfcases.kt4u;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class PackingExcelDownload {
    private static final String sheetName = "packings";

    public Workbook from(final List<ReadErrorPackingResponse> parcelPostList) {
        final List<String[]> data = createExcelData(parcelPostList);
        return createWorkbook(data);
    }

    private List<String[]> createExcelData(final List<ReadErrorPackingResponse> packingList) {
        final List<String[]> data = new ArrayList<>();
        data.add(header());

        for (final ReadErrorPackingResponse packing : packingList) {
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

    private String getSheetName() {
        return sheetName;
    }

    private String[] header() {
        return new String[]{
                "창고",
                "포장상태",
                "포장오류내역",
                "오류상세내역",
                "배송ID",
                "배송번호",
                "송장번호",
                "배송방식",
                "박스명",
                "실제중량무게",
                "포장완료일시",
                "작업자",
                "작업일시",
        };
    }

    private String[] row(ReadErrorPackingResponse packing) {
        return new String[]{
                packing.warehouseName(),
                String.valueOf(packing.status().getDescription()),
                packing.packingError(),
                packing.packingErrorMessage(),
                String.valueOf(packing.deliveryId()),
                packing.deliverySeq(),
                packing.trackingNumber(),
                packing.carrierName(),
                packing.boxName(),
                null == packing.weight() ? "" : String.valueOf(packing.weight()),
                ExcelFormatter.formatDate(packing.completedAt()),
                ExcelFormatter.formatUser(packing.updatedUserName(), packing.updatedUserLoginId()),
                ExcelFormatter.formatDate(packing.updatedAt())
        };
    }
}