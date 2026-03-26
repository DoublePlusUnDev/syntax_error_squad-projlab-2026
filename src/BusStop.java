public class BusStop extends Node {

    @Override
    public void accept(Vehicle vehicle) {
        TestUtil.enterFunction("BusStop:enter(vehicle)");
        vehicle.enterBusStop(this);
        TestUtil.exitFunction("accepted");
    }
    
}
