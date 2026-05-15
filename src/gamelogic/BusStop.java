package gamelogic;

import utils.Logger;

/**
 * Special type of node the vehicle can enter. Acts as a busstop
 * and will invoke the vehicle's correspondig callback on enter.
 */
public class BusStop extends Node {

    public BusStop(String id){
        super(id);
    }

    @Override
    public void accept(Vehicle vehicle) {
        Logger.logLine("VEHICLE [" + vehicle.id + "] ENTERED [" + id + "] BUS STOP "); 
        vehicle.enterBusStop(this);
    }
    
    @Override
    public void inspect() {
        Logger.logLine("Bus stop " + id);
    }
}
