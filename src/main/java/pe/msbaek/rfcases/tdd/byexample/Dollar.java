package pe.msbaek.rfcases.tdd.byexample;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Dollar extends Money {
    protected Dollar(int amount) {
        super(amount, "USD");
    }
}