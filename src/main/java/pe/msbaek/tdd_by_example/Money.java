package pe.msbaek.tdd_by_example;

public abstract class Money {
    protected final int amount;

    public Money(final int amount) {this.amount = amount;}

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
