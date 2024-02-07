package pe.msbaek.rfcases.repeating_switch;

import static pe.msbaek.rfcases.repeating_switch.ParrotTypeEnum.EUROPEAN;

public class EuropeanParrot extends Parrot {
    public EuropeanParrot(boolean isNailed) {
        super(EUROPEAN, isNailed);
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