package pe.msbaek.tdd_by_example;

public class Dollar extends Money {
    protected Dollar(final int amount, String currency) {
        super(amount, currency);
    }

    public Money times(final int multiply) {
        return new Dollar(amount * multiply, currency);
    }

    @Override
    public String currency() {
        return currency;
    }
}
