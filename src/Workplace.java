import java.util.List;

/**
 * Special type of node the vehicle can enter. Acts as a workplace
 * and will invoke the vehicle's correspondig callback on enter.
 * 
 * Will attempt spawn the car back a few turns after it entered the workplace.
 */
public class Workplace extends Node implements Updatable {
    List<Car> restingCars;

    int spawnTimer;

    private static final int TIME_BEFORE_SPAWN_ATTEMPT = 5;

    public Workplace() {
        spawnTimer = TIME_BEFORE_SPAWN_ATTEMPT;

        GameLogic.getInstance().registerUpdatable(this);
    }

    @Override
    public void update() {
        spawnTimer--;

        if (spawnTimer == 0){
            //spawn card
            spawnTimer = TIME_BEFORE_SPAWN_ATTEMPT;
        }
    }

    @Override
    public void accept(Vehicle vehicle) {
        vehicle.enterWorkPlace(this);

        
    }

    public void carParked(Car car){
        restingCars.add(car);
    }
    
    
}
