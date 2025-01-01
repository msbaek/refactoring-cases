package pe.msbaek.tdd_by_example;

public class Dollar extends Money {
    public Dollar(final int amount) {
        super(amount);
    }

    public Dollar times(final int multiply) {
        return new Dollar(amount * multiply);
    }

    @Override
    public boolean equals(final Object obj) {
       Dollar dollar = (Dollar) obj;
       return dollar.amount == this.amount;
    }
}
