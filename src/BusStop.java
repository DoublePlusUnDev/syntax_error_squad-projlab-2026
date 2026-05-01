/**
 * Special type of node the vehicle can enter. Acts as a busstop
 * and will invoke the vehicle's correspondig callback on enter.
 */
public class BusStop extends Node {

    BusStop(String id){
        super(id);
    }

    @Override
    public void accept(Vehicle vehicle) { 
        vehicle.enterBusStop(this);
    }
    
    @Override
    public String inspect() {
        return "Bus stop" + id;
    }
}
