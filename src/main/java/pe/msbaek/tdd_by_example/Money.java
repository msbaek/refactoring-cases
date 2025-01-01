package pe.msbaek.tdd_by_example;

public class Money {
    protected final int amount;
    protected final String currency;

    public Money(final int amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Dollar dollar(final int amount) {
        return new Dollar(amount, "USD");
    }

    public static Franc franc(final int amount) {
        return new Franc(amount, "CHF");
    }

    @Override
    public boolean equals(final Object obj) {
        Money dollar = (Money) obj;
        return dollar.amount == this.amount
               && this.getClass().equals(dollar.getClass());
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    public Money times(final int multiply) {
        return new Money(amount * multiply, currency);
    }

    public String currency() {
        return currency;
    }
}
