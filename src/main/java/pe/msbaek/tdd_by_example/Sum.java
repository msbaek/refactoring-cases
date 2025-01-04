package pe.msbaek.tdd_by_example;

public class Sum implements Expression {
    public Expression augend;
    public Expression addend;

    public Sum(final Expression augend, final Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    public Money reduce(Bank bank, final String to) {
        int amount = augend.reduce(bank, to).amount
                     + addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    @Override
    public Expression plus(final Expression added) {
        return new Sum(this, added);
    }
}
