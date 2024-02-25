package pe.msbaek.rfcases.tdd.byexample;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Dollar {
    private int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public void times(int times) {
        this.amount *= times;
    }
}