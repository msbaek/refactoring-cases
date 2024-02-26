package pe.msbaek.rfcases.tdd.byexample;

public class Bank {
    public Money reduce(Expression source, String to) {
        if(source instanceof Money money) {
            return money.reduce(to);
        }
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}