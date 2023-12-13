package pe.msbaek.rfcases.valueobject;

public record StockChecker(StockCheckStatus stockCheckStatus, int stock, int orderQuantity) {
}