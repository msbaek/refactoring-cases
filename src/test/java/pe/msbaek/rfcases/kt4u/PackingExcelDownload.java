package pe.msbaek.rfcases.kt4u;

public class PackingExcelDownload extends ExcelDownload {
    private static final String sheetName = "packings";

    @Override
    protected String getSheetName() {
        return sheetName;
    }

    @Override
    protected String[] header() {
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

    @Override
    protected String[] row(ReadErrorPackingResponse packing) {
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