package gamelogic;
import java.util.List;

import utils.Logger;

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

    public Workplace(String id) {
        super(id);
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
        Logger.logLine("VEHICLE [" + vehicle.id + "] ENTERED [" + id + "] WORKPLACE ");
        vehicle.enterWorkPlace(this);
    }

    public void parkCar(Car car){
        restingCars.add(car);
    }
    
    @Override
    public void inspect() {
        Logger.logLine("Workplace " + id + " details:");
        Logger.logLine("Spawn timer: " + spawnTimer);
        Logger.logLine("Resting cars: " + restingCars.size());
        for (Car car : restingCars) {
            Logger.logLine("-" + car.id);
        }
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }
    
}
