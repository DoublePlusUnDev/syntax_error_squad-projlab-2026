/**
 * An abstract base for vehicles.
 * Can enter lanes and different types of nodes.
 * May exhibit different behaviours upon crashing into different vehicles.
 * Must report whether it can enter a given lane for pathfinding purposes.
 * May or may not be able to slip on ice.
 */
public abstract  class Vehicle {
    String id;
    protected Lane location;

    public Vehicle(String id){
        this.id = id;
    }

    public void enter(Lane lane) {
        TestUtil.enterFunction("Vehicle:enter(Lane lane)");
        
        location = lane;

        TestUtil.exitFunction("entered lane");
    }

    public void enterApartment(Apartment apartment) {

    }

    public void enterWorkPlace(Workplace workplace) {

    }

    public void enterBusStop(BusStop busStop){

    }

    public void crash(Lane lane) {
        TestUtil.enterFunction("Vehicle:crash()");
        
        TestUtil.exitFunction("crashed");
    }

    public boolean canEnter(Lane lane) {
        TestUtil.enterFunction("Vehicle:canEnter()");

        if (lane.isBlocked()){
            TestUtil.exitFunction("cant enter lane is blocked");
            return false;
        }

        if (lane.isSnowy()){
            TestUtil.exitFunction("cant enter lane is snowy");
            return false;
        }

        if (lane.isDebrisFilled()){
            TestUtil.exitFunction("cant enter lane is full of debris");
            return false;
        }

        TestUtil.exitFunction("lane can be entered");
        return true;
    }

    public boolean canSlip() {
        TestUtil.enterFunction("canSlip()");
    
        boolean slip = TestUtil.askUserYesNo("Can the vehicle slip?");

        TestUtil.exitFunction(String.valueOf(slip));
        return slip;
    }
}
