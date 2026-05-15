package gamelogic;
import java.util.ArrayList;
import java.util.List;

import utils.Logger;

/**
 * A player that is in control of one or more snowplow(s).
 */
public class SnowPlowPlayer extends Player {
    
    private List<SnowPlow> snowPlows;

    public SnowPlowPlayer(String id, RoadNetwork roads, Lane startingLane) {
        super(id, roads);
        snowPlows = new ArrayList<>();
        addSnowPlow(new SnowPlow(id + ".snowPlow1"), startingLane);
    }

    @Override
    public void takeTurn() {
        if (!roads.tryMoveTowardsNode(snowPlows.get(0), null)) 
            return;
    }      
    

    public List<SnowPlow> getSnowPlows() {
        return snowPlows;
    }

    public void addSnowPlow(SnowPlow snowPlow, Lane location) {
        snowPlows.add(snowPlow);
        snowPlow.location = location;
        roads.placeSnowPlow(snowPlow);
    }

    @Override
    public void inspect() {
        Logger.logLine("SnowPlowPlayer " + id + " details:");
        for (int i = 0; i < snowPlows.size(); i++) {
            Logger.logLine("Snowplow " + i + ": " + snowPlows.get(i).id);
        }
    }
}
