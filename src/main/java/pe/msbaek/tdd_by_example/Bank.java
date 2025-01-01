package pe.msbaek.tdd_by_example;

public class Bank {
    public Money reduce(final Expression source, final String to) {
        Sum sum = (Sum) source;
        int amout = sum.augend.amount + sum.addend.amount;
        return new Money(amout, to);
    }
}
