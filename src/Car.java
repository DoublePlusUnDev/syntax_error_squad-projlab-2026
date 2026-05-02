/**
 * A vehicle that is meant to be piloted by the game.
 * Has a home(from which it spawns) and a workplace(it's first target, where it'll rest for a few turns),
 *  then return to it's home and despawn.
 * Cannot enter blocked, snowy or debris filled roads.
 * Can slip, on crash it'll be destroyed and will block the lane. 
 */
public class Car extends Vehicle {
    private boolean isDestroyed = false;
    private Apartment home;
    private Workplace workplace;
    private boolean headedHome = false;

     
    public Car(String id) {
        super(id);
    }

    public void move() {
        if (isDestroyed) {
            return; // Cannot move if destroyed
        }

        Node target = headedHome ? home : workplace;

        if (target == null)
            return; // No target to move towards
        
        


    }

    // Getters

    /**
     * Checks if this car has been destroyed.
     *
     * @return true if the car is destroyed
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    // Setters

    /**
     * Sets the home apartment for this car.
     *
     * @param apartment the home apartment
     */
    public void setApartment(Apartment apartment) {
        this.home = apartment;
    }

    /**
     * Sets the workplace destination for this car.
     *
     * @param workplace the workplace to work at
     */
    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    // Overridden Methods

    @Override
    public boolean canSlip() {
        return true;
    }

    @Override
    public void crash(Lane lane) {
        super.crash(lane);
        lane.crashOccured();
        isDestroyed = true;
    }

    @Override
    public void enterWorkPlace(Workplace workplace) {
        workplace.carParked(this);
    }

    @Override
    public void inspect() {
        Logger.logLine("Car " + id + " details:");
        Logger.logLine("Location: " + (location != null ? location.id : "none"));
        Logger.logLine("Destroyed: " + (isDestroyed ? "yes" : "no"));
    }
}
