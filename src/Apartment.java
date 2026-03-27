/**
 * Represents an apartment building as a node.
 * Spawns cars, also serves as a target to do the cars,
 *  after they return from their respective workplaces.
 * 
 * Responsible for attempeting to spawn cars, at given time periods.
 */
public class Apartment extends Node implements Updatable {

    private int spawnTimer;

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void accept(Vehicle vehicle) {
        TestUtil.enterFunction("Apartment:accept()");
        
        vehicle.enterApartment(this);

        TestUtil.exitFunction("apartment accepted vehicle");
    }

    
}
