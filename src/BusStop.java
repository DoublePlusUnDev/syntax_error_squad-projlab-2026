public class BusStop extends Node {

    @Override
    public void accept(Vehicle vehicle) {
        TestUtil.enterFunction("BusStop:accept()");
        
        vehicle.enterBusStop(this);

        TestUtil.exitFunction("bus stop accepted vehicle");
    }
    
}
