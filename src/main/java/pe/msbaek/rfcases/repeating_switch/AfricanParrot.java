package pe.msbaek.rfcases.repeating_switch;

import static pe.msbaek.rfcases.repeating_switch.ParrotTypeEnum.AFRICAN;

public class AfricanParrot extends Parrot {
    protected final int numberOfCoconuts;

    public AfricanParrot(int numberOfCoconuts) {
        super(AFRICAN);
        this.numberOfCoconuts = numberOfCoconuts;
    }

    @Override
    public String getCry() {
        return "Sqaark!";
    }

    @Override
    public double getSpeed() {
        return Math.max(0, getBaseSpeed() - getLoadFactor() * numberOfCoconuts);
    }
}
