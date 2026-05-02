/**
 * A vehicle that is meant to be piloted by the game.
 * Has a home(from which it spawns) and a workplace(it's first target, where it'll rest for a few turns),
 *  then return to it's home and despawn.
 * Cannot enter blocked, snowy or debris filled roads.
 * Can slip, on crash it'll be destroyed and will block the lane. 
 */
public class Car extends Vehicle {
    boolean isDestroyed = false;
    Apartment home;
    Workplace workplace;

    public Car(String id){
        super(id);
    }

    @Override
    public void crash(Lane lane) {
        lane.crashOccured();
        isDestroyed = true;
    }
    
    public boolean canSlip() {
        return true;
    }

    public void setApartment(Apartment apartment) {
        this.home = apartment;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public boolean isDestroyed() {
        return isDestroyed;
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
