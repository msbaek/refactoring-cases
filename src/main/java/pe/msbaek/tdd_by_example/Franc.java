package pe.msbaek.tdd_by_example;

public class Franc extends Money {

    protected Franc(final int amount, String currency) {
        super(amount, currency);
    }

    public Money times(final int multiply) {
        return this.franc(amount * multiply);
    }

    @Override
    public String currency() {
        return currency;
    }
}
