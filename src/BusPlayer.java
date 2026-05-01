/**
 * A player who controls a singular bus vehicle.
 */
public class BusPlayer extends Player {

    private Bus bus;

    public BusPlayer(String id, RoadNetwork roads) {
        super(id, roads);
        bus = new Bus("");
    }

    @Override
    public void takeTurn() {
        TestUtil.enterFunction("BusPlayer:takeTurn()");
        roads.tryMoveTowardsNode(bus, null);
        TestUtil.exitFunction("turn done");
    }

    public Bus getBus() {
        return bus;
    }

    @Override
    public void inspect() {
        Logger.logLine("BusPlayer " + id + " details:");
        Logger.logLine("Inventory: " + inventory.id);
        Logger.logLine("Bus: " + bus.id);
    }
    
}
