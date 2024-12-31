package pe.msbaek.tdd_by_example;

public class Dollar {
    int amount = 10;

    public Dollar(final int amount) {
        this.amount = amount;
    }

    public Dollar times(final int multiply) {
        return new Dollar(amount * multiply);
    }
}
