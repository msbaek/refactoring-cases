package pe.msbaek.tdd_by_example;

public class Sum implements Expression {
    public Money augend;
    public Money addend;

    public Sum(final Money augend, final Money addend) {
        this.augend = augend;
        this.addend = addend;
    }
}
