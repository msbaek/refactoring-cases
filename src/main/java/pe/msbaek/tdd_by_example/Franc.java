package pe.msbaek.tdd_by_example;

public class Franc extends Money {
    private final int amount;

    public Franc(final int amount) {
        this.amount = amount;
    }

    public Franc times(final int multiply) {
        return new Franc(amount * multiply);
    }

    @Override
    public boolean equals(final Object obj) {
       Franc dollar = (Franc) obj;
       return dollar.amount == this.amount;
    }
}
