package pe.msbaek.tdd_by_example;

public class Dollar extends Money {
    protected Dollar(final int amount) {
        super(amount, "USD");
    }

    public Money times(final int multiply) {
        return dollar(amount * multiply);
    }

    @Override
    public String currency() {
        return currency;
    }
}
