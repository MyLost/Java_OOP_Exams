package christmasRaces.repositories;

import christmasRaces.entities.cars.Car;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarRepository implements Repository<Car> {

    private Map<String, Car> cars;

    public CarRepository() {
        this.cars = new LinkedHashMap<>();
    }

    @Override
    public Car getByName(String name) {
        return cars.get(name);
    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableCollection(cars.values());
    }

    @Override
    public void add(Car model) {
        cars.put(model.getModel(), model);
    }

    @Override
    public boolean remove(Car model) {
        return cars.remove(model) != null;
    }
}
