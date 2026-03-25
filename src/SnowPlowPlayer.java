public class SnowPlowPlayer extends Player {
    
    private SnowPlow snowPlow;

    public SnowPlowPlayer(RoadNetwork roads) {
        super(roads);
    }

    @Override
    public void takeTurn() {
        System.out.println("SnowPlow:TakeTurn");
        if (!roads.canMoveVehicle(snowPlow))
            return;
    }

    

    public SnowPlow getSnowPlow() {
        return snowPlow;
    }
}
