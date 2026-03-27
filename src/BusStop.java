/**
 * Special type of node the vehicle can enter. Acts as a busstop
 * and will invoke the vehicle's correspondig callback on enter.
 */
public class BusStop extends Node {

    @Override
    public void accept(Vehicle vehicle) {
        TestUtil.enterFunction("BusStop:accept()");
        
        vehicle.enterBusStop(this);

        TestUtil.exitFunction("bus stop accepted vehicle");
    }
    
}
