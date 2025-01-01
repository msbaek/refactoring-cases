package pe.msbaek.tdd_by_example;

public class Dollar extends Money {
    public Dollar(final int amount) {
        super(amount);
    }

    public Money times(final int multiply) {
        return new Dollar(amount * multiply);
    }
}
