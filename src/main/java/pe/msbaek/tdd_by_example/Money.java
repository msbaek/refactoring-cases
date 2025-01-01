package pe.msbaek.tdd_by_example;

public class Money {
    protected final int amount;

    public Money(final int amount) {this.amount = amount;}

    @Override
    public boolean equals(final Object obj) {
       Money dollar = (Money) obj;
       return dollar.amount == this.amount
               && this.getClass().equals(dollar.getClass());
    }
}
