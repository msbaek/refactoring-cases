package pe.msbaek.rfcases.repeating_switch;

import static pe.msbaek.rfcases.repeating_switch.ParrotTypeEnum.NORWEGIAN_BLUE;

public class NorwegianBlueParrot extends Parrot {
    public NorwegianBlueParrot(double voltage, boolean isNailed) {
        super(NORWEGIAN_BLUE, voltage, isNailed);
    }

    @Override
    public String getCry() {
        return voltage > 0 ? "Bzzzzzz" : "...";
    }

    @Override
    public double getSpeed() {
        return (isNailed) ? 0 : getBaseSpeed(voltage);
    }
}
