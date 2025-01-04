package pe.msbaek.tdd_by_example;

import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<Pair<String, String>, Integer> rates = new HashMap<>();

    public Money reduce(final Expression source, final String to) {
        return source.reduce(this, to);
    }

    public void addRate(final String from, final String to, final int amount) {
        rates.put(new Pair(from, to), amount);
    }

    int rate(String currency, final String to) {
        if (currency.equals(to)) {
            return 1;
        }
        return rates.get(new Pair(currency, to));
    }
}