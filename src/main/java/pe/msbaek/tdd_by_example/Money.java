package pe.msbaek.tdd_by_example;

public abstract class Money {
    protected final int amount;
    protected final String currency;

    public Money(final int amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Dollar dollar(final int amount) {
        return new Dollar(amount);
    }

    public static Franc franc(final int amount) {
        return new Franc(amount);
    }

    @Override
    public boolean equals(final Object obj) {
        Money dollar = (Money) obj;
        return dollar.amount == this.amount
               && this.getClass().equals(dollar.getClass());
    }

    public abstract Money times(final int multiply);

    public abstract String currency();
}
