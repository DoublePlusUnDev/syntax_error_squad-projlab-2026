/**
 * A vehicle that is meant to be piloted by the game.
 * Has a home(from which it spawns) and a workplace(it's first target, where it'll rest for a few turns),
 *  then return to it's home and despawn.
 * Cannot enter blocked, snowy or debris filled roads.
 * Can slip, on crash it'll be destroyed and will block the lane. 
 */
public class Car extends Vehicle {

    @Override
    public void crash(Lane lane) {
        TestUtil.enterFunction("Car:crash(lane)");
        lane.crashOccured();
        TestUtil.exitFunction("car crashed");
    }
    
    
}
