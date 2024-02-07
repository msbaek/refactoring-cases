package pe.msbaek.rfcases.repeating_switch;

public abstract class Parrot {
    protected final ParrotTypeEnum type;
    private final int numberOfCoconuts;
    protected final double voltage;
    private final boolean isNailed;

    protected Parrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {
        this.type = type;
        this.numberOfCoconuts = numberOfCoconuts;
        this.voltage = voltage;
        this.isNailed = isNailed;
    }

    public static Parrot createParrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {
        return switch (type) {
            case EUROPEAN -> new EuropeanParrot(numberOfCoconuts, voltage, isNailed);
            case AFRICAN -> new AfricanParrot(numberOfCoconuts, voltage, isNailed);
            case NORWEGIAN_BLUE -> new NorwegianBlueParrot(numberOfCoconuts, voltage, isNailed);
        };
    }

    public abstract double getSpeed();

    private double getBaseSpeed(double voltage) {
        return Math.min(24.0, voltage * getBaseSpeed());
    }

    private double getLoadFactor() {
        return 9.0;
    }

    private double getBaseSpeed() {
        return 12.0;
    }

    public abstract String getCry();
}