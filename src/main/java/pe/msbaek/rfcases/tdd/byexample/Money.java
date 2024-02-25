package pe.msbaek.rfcases.tdd.byexample;

public class Money {
    protected int amount;
    protected final String currency;

    protected Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    public static Franc franc(int amount) {
        return new Franc(amount);
    }

    public Money times(int times) {
        return new Money(this.amount * times, this.currency);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return money.amount == this.amount;
    }

    public int amount() {
        return this.amount;
    }

    public String currency() {
        return this.currency;
    }
}