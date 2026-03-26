public class BusPlayer extends Player {

    private Bus bus;

    public BusPlayer(RoadNetwork roads) {
        super(roads);
        
    }

    @Override
    public void takeTurn() {
        TestUtil.enterFunction("BusPlayer:takeTurn()");
        roads.tryMoveTowardsNode(bus, null);
        TestUtil.exitFunction("turn done");
    }

    
    
}
