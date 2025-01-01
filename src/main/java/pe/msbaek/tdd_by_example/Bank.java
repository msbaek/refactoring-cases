package pe.msbaek.tdd_by_example;

public class Bank {
    public Money reduce(final Expression source, final String to) {
        Sum sum = (Sum) source;
        return reduce(to, sum);
    }

    private Money reduce(final String to, final Sum sum) {
        int amount = sum.augend.amount + sum.addend.amount;
        return new Money(amount, to);
    }
}
