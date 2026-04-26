/**
 * Represents an apartment building as a node.
 * Spawns cars, also serves as a target to do the cars,
 *  after they return from their respective workplaces.
 * 
 * Responsible for attempeting to spawn cars, at given time periods.
 */
public class Apartment extends Node implements Updatable {

    private int spawnTimer;
    private static final int TIME_BETWEEN_SPAWN_ATTEMPTS = 10;

    public Apartment() {
        spawnTimer = TIME_BETWEEN_SPAWN_ATTEMPTS;

        GameLogic.getInstance().registerUpdatable(this);
    }

    @Override
    public void update() {
        spawnTimer--;

        if (spawnTimer == 0){
            trySpawnCar();
            spawnTimer = TIME_BETWEEN_SPAWN_ATTEMPTS;
        }
    }

    @Override
    public void accept(Vehicle vehicle) {
        vehicle.enterApartment(this);
    }

    private void trySpawnCar(){

    }
}
