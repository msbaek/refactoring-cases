package pe.msbaek.rfcases.tdd.byexample;

public abstract class Money {
    protected int amount;

    protected Money(int amount) {
        this.amount = amount;
    }

    public static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    public static Franc franc(int amount) {
        return new Franc(amount);
    }

    public abstract Money times(int times);

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return money.amount == this.amount;
    }

    public int amount() {
        return this.amount;
    }

    public abstract String currency();
}
