package pe.msbaek.rfcases.tdd.byexample;

public class Money {
    protected int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public Dollar times(int times) {
        return new Dollar(this.amount * times);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return money.amount == this.amount;
    }

    public int amount() {
        return this.amount;
    }
}
