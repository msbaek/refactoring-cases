package pe.msbaek.rfcases.tdd.byexample;

public class Franc extends Money {
    protected Franc(int amount) {
        super(amount,"CHF");
    }

    @Override
    public Franc times(int times) {
        return Money.franc(this.amount * times);
    }
}