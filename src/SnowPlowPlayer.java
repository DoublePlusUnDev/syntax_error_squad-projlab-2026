import java.util.ArrayList;
import java.util.List;

public class SnowPlowPlayer extends Player {
    
    private List<SnowPlow> snowPlows;

    public SnowPlowPlayer(RoadNetwork roads) {
        super(roads);
        snowPlows = new ArrayList<>();
        snowPlows.add(new SnowPlow());
    }

    @Override
    public void takeTurn() {
        TestUtil.enterFunction("SnowPlow:TakeTurn()");
        if (!roads.tryMoveTowardsNode(snowPlows.get(0), null))
            return;

        TestUtil.exitFunction("turnDone");        
    }

    

    public List<SnowPlow> getSnowPlows() {
        return snowPlows;
    }
}
