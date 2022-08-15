package christmasRaces.entities.races;

import christmasRaces.entities.drivers.Driver;

import java.util.ArrayList;
import java.util.Collection;

import static christmasRaces.common.ExceptionMessages.*;

public class RaceImpl implements Race {

    private static final int RACE_NAME_MIN_LENGHT = 5;

    private static final int MIN_LAPS = 1;

    private String name;

    private int laps;

    private Collection<Driver> drivers;

    public RaceImpl(String name, int laps) {
        this.setName(name);
        this.setLaps(laps);
        drivers = new ArrayList<>();
    }

    private void setName(String name) {
        if(name == null || name.trim().isEmpty() || name.length() < RACE_NAME_MIN_LENGHT) {
            throw new IllegalArgumentException(String.format(INVALID_NAME, name, RACE_NAME_MIN_LENGHT));
        }
        this.name = name;
    }

    private void setLaps(int laps) {
        if(laps < MIN_LAPS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_LAPS, laps));
        }
        this.laps = laps;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLaps() {
        return laps;
    }

    @Override
    public Collection<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(DRIVER_INVALID);
        } else if(!driver.getCanParticipate()) {
            throw new IllegalArgumentException(String.format(DRIVER_NOT_PARTICIPATE, driver.getName()));
        } else if(drivers.contains(driver)) {
            throw new IllegalArgumentException(String.format(DRIVER_ALREADY_ADDED, driver.getName(), name));
        }
        drivers.add(driver);
    }
}
