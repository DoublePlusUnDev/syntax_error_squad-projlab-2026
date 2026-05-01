import java.util.ArrayList;
import java.util.List;

/**
 * A player that is in control of one or more snowplow(s).
 */
public class SnowPlowPlayer extends Player {
    
    private List<SnowPlow> snowPlows;

    public SnowPlowPlayer(String id, RoadNetwork roads) {
        super(id, roads);
        snowPlows = new ArrayList<>();
        snowPlows.add(new SnowPlow(""));
    }

    @Override
    public void takeTurn() {
        TestUtil.enterFunction("SnowPlow:takeTurn()");
        if (!roads.tryMoveTowardsNode(snowPlows.get(0), null)) {
            TestUtil.exitFunction("turn failed");    
            return;
        }
        
        TestUtil.exitFunction("turn done");        
    }

    public List<SnowPlow> getSnowPlows() {
        return snowPlows;
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("SnowPlowPlayer " + id + " details:\n");
        output.append("Inventory: " + inventory.id + "\n");
        for (int i = 0; i < snowPlows.size(); i++) {
            output.append("Snowplow " + i + ": " + snowPlows.get(i).id + "\n");
        }
        return output.toString();
    }
}
