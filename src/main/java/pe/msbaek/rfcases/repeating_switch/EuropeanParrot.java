package pe.msbaek.rfcases.repeating_switch;

import static pe.msbaek.rfcases.repeating_switch.ParrotTypeEnum.EUROPEAN;

public class EuropeanParrot extends Parrot {
    public EuropeanParrot(int numberOfCoconuts, double voltage, boolean isNailed) {
        super(EUROPEAN, numberOfCoconuts, voltage, isNailed);
    }

    @Override
    public String getCry() {
        return "Sqoork!";
    }

    @Override
    public double getSpeed() {
        return getBaseSpeed();
    }
}