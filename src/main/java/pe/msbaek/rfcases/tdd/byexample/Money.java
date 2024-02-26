package pe.msbaek.rfcases.tdd.byexample;

public class Money implements Expression {
    protected int amount;
    protected final String currency;

    protected Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public Money times(int times) {
        return new Money(this.amount * times, this.currency);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return money.amount == this.amount && this.currency.equals(money.currency);
    }

    public int amount() {
        return this.amount;
    }

    public String currency() {
        return this.currency;
    }

    public Expression plus(Money addend) {
        return new Sum(this, addend);
    }

    public Money reduce(Bank bank, String to) {
        int rate = bank.rate(to, this);
        return new Money(amount / rate, to);
    }
}