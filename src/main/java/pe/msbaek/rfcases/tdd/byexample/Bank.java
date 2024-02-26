package pe.msbaek.rfcases.tdd.byexample;

import java.util.HashMap;
import java.util.Map;

record Pair(String from, String to) {
    public boolean equals(Object object) {
        Pair pair = (Pair) object;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    public int hashCode() {
        return 0;
    }
}

public class Bank {
    private Map<Pair, Integer> currencyRates = new HashMap<>();

    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public void addRate(String from, String to, int rate) {
        currencyRates.put(new Pair(from, to), rate);
    }

    int rate(String to, Money money) {
        return currencyRates.getOrDefault(new Pair(money.currency(), to), 1);
    }
}