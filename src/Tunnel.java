/**
 * A type of road that is immune to snowfall.
 */
public class Tunnel extends RoadSegment {

    public Tunnel(String id, int laneCount, Node startPoint, Node endPoint) {
        super(id, laneCount, startPoint, endPoint);
    }

    @Override
    public void addSnow(float snowLevel) {
        
    }

    @Override
    public void inspect() {
        Logger.logLine("Tunnel " + id + " details:");
        Logger.logLine("Start Point: " + startPoint.id);
        Logger.logLine("End Point: " + endPoint.id);
        Logger.logLine("Lanes:");
        for (int i = 0; i < lanes.size(); i++) {
            Logger.logLine("  Lane " + i + ": " + lanes.get(i).id);
        }
    }

    
}
