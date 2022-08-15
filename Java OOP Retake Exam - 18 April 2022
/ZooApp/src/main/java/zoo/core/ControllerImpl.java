package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegitable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.*;
import java.util.stream.Collectors;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private FoodRepository foodRepository;
    private Map<String, Area> areas;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.areas = new LinkedHashMap<>();
    }

    @Override
    public String addArea(String areaType, String areaName) {

        Area area;

        switch(areaType) {
            case "WaterArea":
                area = new WaterArea(areaName);
                break;
            case "LandArea":
                area = new LandArea(areaName);
                break;
            default:
                throw new NullPointerException(INVALID_AREA_TYPE);
        }

        areas.put(area.getName(), area);
        return String.format(SUCCESSFULLY_ADDED_AREA_TYPE, areaType);
    }

    @Override
    public String buyFood(String foodType) {
        Food food;

        switch(foodType) {
            case "Vegatable":
                food = new Vegitable();
                break;
            case "Meat":
                food = new Meat();
                break;
            default:
                throw new NullPointerException(INVALID_FOOD_TYPE);
        }

       foodRepository.add(food);
        return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE, foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType)
    {
        Food food = foodRepository.findByType(foodType);

        if(food == null) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND, foodType));
        }

        Area area = areas.get(areaName);
        area.addFood(food);

        foodRepository.remove(food);
        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA, foodType, areaName);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {

        Animal animal;

       switch (animalType) {
           case "AquaticAnimal":
               animal = new AquaticAnimal(animalName, kind, price);
               break;
           case "TerrestrialAnimal":
               animal = new TerrestrialAnimal(animalName, kind, price);
               break;
           default:
               throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
       }

       Area area =  areas.get(areaName);

       String areaType = area.getClass().getSimpleName();

        boolean areaAndAnimalAreLandBased =  areaType.equals("LandArea") && animalType.equals("TerrestrialAnimal");
        boolean areaAndAnimalAreWaterBased = areaType.equals("WaterArea") && animalType.equals("AquaticAnimal");

       if(areaAndAnimalAreLandBased || areaAndAnimalAreWaterBased) {
           area.addAnimal(animal);
       } else {
           return AREA_NOT_SUITABLE;
       }

       return String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA, animalType, areaName);
    }

    @Override
    public String feedAnimal(String areaName) {

        Area area = areas.get(areaName);

        area.feed();

        return String.format(ANIMALS_FED, area.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {

        Collection<Animal> animals = areas.get(areaName).getAnimals();

        double kgs = animals.stream()
                .mapToDouble(Animal::getKg)
                .sum();

        return String.format(KILOGRAMS_AREA, areaName, kgs);
    }

    @Override
    public String getStatistics() {
        return areas.values().stream()
                .map(Area::getInfo)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
