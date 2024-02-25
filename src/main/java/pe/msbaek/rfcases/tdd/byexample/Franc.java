package pe.msbaek.rfcases.tdd.byexample;

public class Franc extends Money {
    protected Franc(int amount) {
        super(amount);
    }

    @Override
    public Franc times(int times) {
        return Money.franc(this.amount * times);
    }

    @Override
    public String currency() {
        return "CHF";
    }
}