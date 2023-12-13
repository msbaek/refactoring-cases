package pe.msbaek.rfcases.valueobject;

import java.time.LocalDate;

public record StockChecker(StockCheckStatus stockCheckStatus, int stock, int orderQuantity) {
    private boolean isStock() {
        final Integer stock = stock();
        if (null == stock) return true;
        return 0 <= stock - (Integer) orderQuantity();
    }

    boolean check() {
        if (!LocalDate.now().isAfter(stockCheckStatus().expectedStockDate())) return true;
        if (stockCheckStatus().forcedSales()) return true;
        return isStock();
    }
}