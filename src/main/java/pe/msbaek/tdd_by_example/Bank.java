package pe.msbaek.tdd_by_example;

public class Bank {
    public Money reduce(final Expression source, final String to) {
        return source.reduce(this, to);
    }

    public void addRate(final String from, final String to, final int amount) {
    }
}