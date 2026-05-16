package gamelogic;

import utils.Logger;

/**
 * An apartment node that spawns cars on a timer and accepts returning vehicles.
 *
 * The apartment is registered as an {@link Updatable} so the game loop can tick
 * its spawn timer once per round.
 */
public class Apartment extends Node implements Updatable {

    private static final int TIME_BETWEEN_SPAWN_ATTEMPTS = 10;

    private int spawnTimer;
    private int spawnedCars = 0;

    public Apartment(String id) {
        super(id);
        spawnTimer = TIME_BETWEEN_SPAWN_ATTEMPTS;

        GameLogic.getInstance().registerUpdatable(this);
    }

    @Override
    public void update() {
        spawnTimer--;

        if (spawnTimer <= 0) {
            trySpawnCar();
            spawnTimer = TIME_BETWEEN_SPAWN_ATTEMPTS;
        }
    }

    /**
     * Passes an arriving vehicle back to the vehicle-specific apartment callback.
     */
    @Override
    public void accept(Vehicle vehicle) {
        Logger.logLine("VEHICLE [" + vehicle.id + "] ENTERED [" + id + "] APARTMENT");
        vehicle.enterApartment(this);
    }

    /**
     * Creates a new apartment car and hands it over to the game logic.
     */
    private void trySpawnCar() {
        Car car = new Car(id + "_" + spawnedCars);
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

        if (freeLane == null)
            return;

        GameLogic.getInstance().addCar(car, freeLane);
        spawnedCars++;
    }

    /**
     * Logs the apartment state for debugging and inspection commands.
     */
    @Override
    public void inspect() {
        Logger.logLine("Apartment " + id + " details:");
        Logger.logLine("Spawn timer: " + spawnTimer);
        Logger.logLine("Spawned cars: " + spawnedCars);
    }

    /**
     * Overrides the remaining ticks until the next spawn attempt.
     */
    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }
}
