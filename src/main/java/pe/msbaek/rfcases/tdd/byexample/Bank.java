package pe.msbaek.rfcases.tdd.byexample;

public class Bank {
    public Money reduce(Expression source, String to) {
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}