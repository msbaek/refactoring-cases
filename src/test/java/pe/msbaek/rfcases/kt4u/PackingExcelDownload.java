package pe.msbaek.rfcases.kt4u;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PackingExcelDownload {
    private static final String sheetName = "packings";

    public static Workbook from(final List<ReadErrorPackingResponse> parcelPostList) {
        final List<String[]> data = createExcelData(parcelPostList);
        return createWorkbook(data);
    }

    static List<String[]> createExcelData(final List<ReadErrorPackingResponse> packingList) {
        final List<String[]> data = new ArrayList<>();
        data.add(new String[]{
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
        });

        for (final ReadErrorPackingResponse packing : packingList) {
            data.add(new String[]{
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
                    formattingDate(packing.completedAt()),
                    formattingUser(packing.updatedUserName(), packing.updatedUserLoginId()),
                    formattingDate(packing.updatedAt())
            });
        }
        return data;
    }

    private static String formattingDate(final Instant value) {
        if (null == value) return null;
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(value);
    }

    private static String formattingUser(final String userName, final String userLoginId) {
        return userName + "(" + userLoginId + ")";
    }

    static Workbook createWorkbook(final List<String[]> data) {
        final Workbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet(sheetName);

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
}