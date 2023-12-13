package pe.msbaek.rfcases.valueobject;

import java.time.LocalDate;

record StockCheckStatus(LocalDate expectedStockDate, boolean forcedSales) {}

record StockCheckRequest(long shopNo, String productNo, int stock, int orderQuantity) {
}
interface StockCheckPort {
    StockCheckStatus readAvailableSalesStatus(final String productNo, final long shopNo);
}
public class StockCheckService {
    private StockCheckPort stockCheckPort;

    public boolean availableForSales(final StockCheckRequest stockCheckRequest) {
        final StockCheckStatus stockCheckStatus = stockCheckPort.readAvailableSalesStatus(stockCheckRequest.productNo(), stockCheckRequest.shopNo());
        return new StockChecker(stockCheckStatus, stockCheckRequest.stock(), stockCheckRequest.orderQuantity()).check();
    }
}