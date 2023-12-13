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
        return check(new StockChecker(stockCheckStatus, stockCheckRequest.stock(), stockCheckRequest.orderQuantity()));
    }

    private boolean check(StockChecker stockChecker) {
        if (!LocalDate.now().isAfter(stockChecker.stockCheckStatus().expectedStockDate())) return true;
        if (stockChecker.stockCheckStatus().forcedSales()) return true;
        return isStock(stockChecker);
    }

    private boolean isStock(StockChecker stockChecker) {
        final Integer stock = stockChecker.stock();
        if (null == stock) return true;
        return 0 <= stock - (Integer) stockChecker.orderQuantity();
    }
}