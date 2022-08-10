package fairyShop.models;

public class Sleepy extends BaseHelper {

    private static final int INITIAL_ENERGY = 50;

    private static final int DECREASE_ENERGY_FACTOR = 5;

    public Sleepy(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void work() {
        setEnergy(Math.max(0 , getEnergy() - DECREASE_ENERGY_FACTOR));
    }
}
