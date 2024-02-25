package pe.msbaek.rfcases.tdd.byexample;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Dollar extends Money {
    protected Dollar(int amount) {
        super(amount);
    }

    @Override
    public Dollar times(int times) {
        return Money.dollar(this.amount * times);
    }

    @Override
    public String currency() {
        return "USD";
    }
}