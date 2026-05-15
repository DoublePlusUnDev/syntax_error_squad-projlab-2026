package gamelogic;

import utils.Logger;

/**
 * A player who controls a singular bus vehicle.
 */
public class BusPlayer extends Player {

    private Bus bus;

    public BusPlayer(String id, RoadNetwork roads, Lane startingLane) {
        super(id, roads);
        bus = new Bus(id + ".bus");
        bus.location = startingLane;
        roads.placeBus(bus);
    }

    @Override
    public void takeTurn() {
        roads.tryMoveTowardsNode(bus, null);
    }

    public Bus getBus() {
        return bus;
    }

    @Override
    public void inspect() {
        Logger.logLine("BusPlayer " + id + " details:");
        Logger.logLine("Bus: " + bus.id);
    }
    
}
