package pe.msbaek.tdd_by_example;

public class Bank {
    public Money reduce(final Expression source, final String to) {
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}