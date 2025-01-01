package pe.msbaek.tdd_by_example;

public class Franc extends Money {
    public Franc(final int amount) {
        super(amount);
    }

    public Money times(final int multiply) {
        return new Franc(amount * multiply);
    }
}
