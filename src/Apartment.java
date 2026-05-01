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

    public Apartment(String id) {
        super(id);
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

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder();
        output.append("Apartment " + id + "details:\n");
        output.append("Spawn timer: " + spawnTimer);
        return output.toString();
    }
}
