package christmasRaces.core.interfaces;

import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.CarRepository;
import christmasRaces.repositories.DriverRepository;
import christmasRaces.repositories.RaceRepository;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static christmasRaces.common.ExceptionMessages.*;
import static christmasRaces.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private static final int MIN_DRIVERS_TO_RACE = 3;
    private CarRepository carRepository;
    private DriverRepository driverRepository;
    private RaceRepository raceRepository;

    public ControllerImpl(Repository driverRepository, Repository carRepository, Repository raceRepository) {
        this.carRepository = (CarRepository) carRepository;
        this.driverRepository = (DriverRepository) driverRepository;
        this.raceRepository = (RaceRepository) raceRepository;
    }

    @Override
    public String createDriver(String driver) {

        if (driverRepository.getByName(driver) != null) {
            throw new IllegalArgumentException(String.format(DRIVER_EXISTS, driver));
        }

        Driver newDriver = new DriverImpl(driver);
        driverRepository.add(newDriver);

        return String.format(DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {

        if (carRepository.getByName(model) != null) {
            throw new IllegalArgumentException(String.format(CAR_EXISTS, model));
        }

        Car car = null;
        if("Muscle".equals(type)) {
            car = new MuscleCar(model, horsePower);
        } else if ("Sports".equals(type)) {
            car = new SportsCar(model, horsePower);
        }

        carRepository.add(car);
        return String.format(CAR_CREATED, car.getClass().getSimpleName(),  model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {

        if(driverRepository.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }

        if(carRepository.getByName(carModel) == null) {
            throw new IllegalArgumentException(String.format(CAR_NOT_FOUND, carModel));
        }

        Car car = (Car) carRepository.getByName(carModel);
        Driver driver = (Driver) driverRepository.getByName(driverName);

        driver.addCar(car);

        return String.format(CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {

        if(driverRepository.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }

        if(raceRepository.getByName(raceName) == null) {
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }

        Race race = (Race) raceRepository.getByName(raceName);
        Driver driver = (Driver) driverRepository.getByName(driverName);

        race.addDriver(driver);

        return String.format(DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {

        Race race = raceRepository.getByName(raceName);

        if(race == null) {
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }

        if(race.getDrivers().size() < MIN_DRIVERS_TO_RACE) {
            throw new IllegalArgumentException(String.format(RACE_INVALID, raceName, MIN_DRIVERS_TO_RACE));
        }

        List<Driver> drivers = driverRepository.getAll()
                .stream()
                .sorted((d1, d2) -> {
                    if(d1.getCar().calculateRacePoints(race.getLaps()) > d2.getCar().calculateRacePoints(race.getLaps())) {
                        return 1;
                    } else if(d1.getCar().calculateRacePoints(race.getLaps()) < d2.getCar().calculateRacePoints(race.getLaps())) {
                        return -1;
                    } else {
                        return 0;
                    }
                })
                .collect(Collectors.toList());

        Collections.reverse(drivers);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(DRIVER_FIRST_POSITION, drivers.get(0).getName(), raceName));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format(DRIVER_SECOND_POSITION, drivers.get(1).getName(), raceName));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format(DRIVER_THIRD_POSITION, drivers.get(2).getName(), raceName));
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {

        if(raceRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(RACE_EXISTS, name));
        }

        Race race = new RaceImpl(name, laps);
        raceRepository.add(race);

        return String.format(RACE_CREATED, name);
    }
}
