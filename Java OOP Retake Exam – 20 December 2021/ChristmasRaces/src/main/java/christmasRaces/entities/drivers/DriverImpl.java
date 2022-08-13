package christmasRaces.entities.drivers;

import christmasRaces.entities.cars.Car;

import static christmasRaces.common.ExceptionMessages.CAR_INVALID;
import static christmasRaces.common.ExceptionMessages.INVALID_NAME;

public class DriverImpl implements Driver {

    private static final int DRIVER_NAME_MIN_LENGHT = 5;
    private String name;

    private Car car;

    private int numberOfWins;

    private boolean canPaticipate;

    public DriverImpl(String name) {
        this.setName(name);
        this.canPaticipate = false;
        this.numberOfWins = 0;
    }

    private void setName(String name) {
        canPaticipate = false;
        if(name == null || name.trim().isEmpty() || name.length() < DRIVER_NAME_MIN_LENGHT) {
            throw new IllegalArgumentException(String.format(INVALID_NAME, name, DRIVER_NAME_MIN_LENGHT));
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public int getNumberOfWins() {
        return numberOfWins;
    }

    @Override
    public void addCar(Car car) {
        if(car == null) {
            throw new IllegalArgumentException(CAR_INVALID);
        }
        this.car = car;
        this.canPaticipate = true;
    }

    @Override
    public void winRace() {
        this.numberOfWins++;
    }

    @Override
    public boolean getCanParticipate() {
        return this.canPaticipate;
    }
}
