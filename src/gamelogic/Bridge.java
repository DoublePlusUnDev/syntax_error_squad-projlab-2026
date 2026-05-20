package gamelogic;

import utils.Logger;

/**
 * A bridge, a type of road which has railing so snow cannot be blown and swept from it.
 * You cannot sweep the snow off of the bridge from the rightmost lane, it will just stay there.
 * When a bridge is blown, the snow will move to the rightmost lane, if it's blown from the rightmost lane
 * it will stay unaffected.
 * 
 */
public class Bridge extends RoadSegment {

    public Bridge(String id, int laneCount, Node startPoint, Node endPoint) {
        super(id, laneCount, startPoint, endPoint);
    }

    /**
     * Sweeps snow from the given lane.
     * 
     * @param lane the lane to sweep
     * 
     * If the lane is the rightmost lane, the snow will not be swept and will stay there.
     * Otherwise, the snow will be moved to the next lane on the right.
     */
    @Override
    public float sweep(Lane lane) {
        if (isRightLane(lane))
            return 0;

        Lane nextLane = lanes.get(lane.getCount() + 1);
        float payout = 0;
        float snowLevel = lane.getSnow();
        payout += lane.destroySnow();
        float gravelLevel = lane.getGravel();
        payout += lane.destroyGravel();
        boolean iceDebris = lane.isDebrisFilled();
        payout += lane.destroyIceDebris();
        
        nextLane.addSnow(snowLevel);
        nextLane.addGravel(gravelLevel);
        nextLane.setIceDebris(iceDebris);

        return payout;
    }

    /**
     * Blows snow from the given lane.
     * 
     * @param lane the lane to blow
     * 
     * If the lane is the rightmost lane, the snow will not be blown and will stay there.
     * Otherwise, the snow will be moved to the rightmost lane.
     */
    @Override
    public float blow(Lane lane) {
        if (isRightLane(lane))
            return 0;

        Lane rightMostLane = lanes.get(lanes.size() - 1);
        float payout = 0;

        float snowLevel = lane.getSnow();
        payout += lane.destroySnow();

        float gravelLevel = lane.getGravel();
        payout += lane.destroyGravel();

        boolean iceDebris = lane.isDebrisFilled();
        payout += lane.destroyIceDebris();

        rightMostLane.addSnow(snowLevel);
        rightMostLane.addGravel(gravelLevel);
        rightMostLane.setIceDebris(iceDebris);

        return payout;
    }

    @Override
    public void inspect() {
        Logger.logLine("Bridge " + id + " details:");
        Logger.logLine("Start Point: " + startPoint.id);
        Logger.logLine("End Point: " + endPoint.id);
        Logger.logLine("Lanes:");
        for (int i = 0; i < lanes.size(); i++) {
            Logger.logLine("  Lane " + i + ": " + lanes.get(i).id);
        }
    }
}
