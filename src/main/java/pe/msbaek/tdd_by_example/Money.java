package pe.msbaek.tdd_by_example;

public class Money implements Expression {
    protected final int amount;
    protected final String currency;

    public Money(final int amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(final int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(final int amount) {
        return new Money(amount, "CHF");
    }

    @Override
    public boolean equals(final Object obj) {
        Money dollar = (Money) obj;
        return dollar.amount == this.amount
               && dollar.currency.equals(this.currency);
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

    public Expression plus(final Money addend) {
        return new Sum(this, addend);
    }

    public Money reduce(final String to) {
        int rate = "CHF".equals(currency) && "USD".equals(to) ? 2 : 1;
        return new Money(amount / rate, to);
    }
}
