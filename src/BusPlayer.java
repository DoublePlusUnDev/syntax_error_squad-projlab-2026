/**
 * A player who controls a singular bus vehicle.
 */
public class BusPlayer extends Player {

    private Bus bus;

    public BusPlayer(RoadNetwork roads) {
        super(roads);
        bus = new Bus();
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
    
}
