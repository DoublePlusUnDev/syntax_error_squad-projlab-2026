/**
 * An abstract base for vehicles.
 * Can enter lanes and different types of nodes.
 * May exhibit different behaviours upon crashing into different vehicles.
 * Must report whether it can enter a given lane for pathfinding purposes.
 * May or may not be able to slip on ice.
 */
public abstract class Vehicle implements Inspectable {
    String id;
    protected Lane location;

    public Vehicle(String id){
        this.id = id;
        ObjectRegistry.register(id, this);
    }

    public void enter(Lane lane) {
        location = lane;
    }

    public void enterApartment(Apartment apartment) {

    }

    public void enterWorkPlace(Workplace workplace) {

    }

    public void enterBusStop(BusStop busStop){

    }

    public void crash(Lane lane) {
        Logger.logLine("VEHICLE [" + id + "] CRASHED AT [" + lane.id + "]");
    }

    public boolean canEnter(Lane lane) {

        if (lane.isBlocked()){
            return false;
        }

        if (lane.isSnowy()){
            return false;
        }

        if (lane.isDebrisFilled()){
            return false;
        }

        return true;
    }

    public boolean canSlip() {
    
        boolean slip = TestUtil.askUserYesNo("Can the vehicle slip?");

        return slip;
    }
}
