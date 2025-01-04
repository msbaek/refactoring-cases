package pe.msbaek.tdd_by_example;

public interface Expression {
    Money reduce(Bank bank, final String to);
    Expression plus(Expression added);
}
