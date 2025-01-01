package pe.msbaek.tdd_by_example;

public class Bank {
    public Money reduce(final Expression source, final String to) {
        if (source instanceof Money) {
            return (Money) source;
        }
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}