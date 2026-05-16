package gamelogic;
import java.util.ArrayList;
import java.util.List;

import utils.Logger;

/**
 * Special type of node the vehicle can enter. Acts as a workplace
 * and will invoke the vehicle's correspondig callback on enter.
 * 
 * Will attempt spawn the car back a few turns after it entered the workplace.
 */
public class Workplace extends Node implements Updatable {
    List<Car> restingCars = new ArrayList<>();

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
            trySpawnCar();
        }
    }

    private void trySpawnCar() {
        if (restingCars.isEmpty())
            return;

        Car car = restingCars.get(0);
        Lane freeLane = null;
        for (Neighbour neighbour : neighbours) {
            for (Lane lane : neighbour.getRoadSegment().getLanes()) {
                if (car.canEnter(lane)) {
                    freeLane = lane;
                    break;
                }
            }
            if (freeLane != null)
                break;
            
        }

        if (freeLane != null){
            restingCars.remove(car);
            GameLogic.getInstance().addCar(car, freeLane);
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
