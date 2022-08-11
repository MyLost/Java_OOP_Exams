package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.INVALID_HORSE_POWER;
import static christmasRaces.common.ExceptionMessages.INVALID_MODEL;

public abstract class BaseCar implements Car {

    private static final int MODEL_MIN_LENGHT = 4;
    private String model;

    private int horsePower;

    private double cubicCentimeters;

    protected BaseCar(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    private void setModel(String model) {
        if(model == null || model.trim().isEmpty() || model.length() < MODEL_MIN_LENGHT) {
            throw new IllegalArgumentException(String.format(INVALID_MODEL, model, MODEL_MIN_LENGHT));
        }
        this.model = model;
    }

    private void setHorsePower(int horsePower) {
        if(this.getClass().getSimpleName().equals(MuscleCar.class.getSimpleName())) {
            if(horsePower > 600 || horsePower < 400) {
                throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));
            }
        } else if(this.getClass().getSimpleName().equals(SportsCar.class.getSimpleName())) {
            if(horsePower > 450 || horsePower < 250) {
                throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));
            }
        }

        this.horsePower = horsePower;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getHorsePower() {
        return horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return cubicCentimeters / horsePower * laps;
    }
}
