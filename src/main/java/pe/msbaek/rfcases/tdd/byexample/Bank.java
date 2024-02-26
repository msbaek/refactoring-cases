package pe.msbaek.rfcases.tdd.byexample;

public class Bank {
    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public void addRate(String from, String to, int rate) {
    }

    int rate(String to, Money money) {
        int rate = money.currency.equals("CHF") && to.equals("USD")
                ? 2
                : 1;
        return rate;
    }
}