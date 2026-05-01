/**
 * A type of road that is immune to snowfall.
 */
public class Tunnel extends RoadSegment {

    public Tunnel(String id, int laneCount, Node startPoint, Node endPoint) {
        super(id, laneCount, startPoint, endPoint);
    }

    @Override
    public void addSnow(int snowLevel) {
        
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("Tunnel " + id + " details:\n");
        output.append("Start Point: " + startPoint.id + "\n");
        output.append("End Point: " + endPoint.id + "\n");
        output.append("Lanes:\n");
        for (int i = 0; i < lanes.size(); i++) {
            output.append("  Lane " + i + ": " + lanes.get(i).id + "\n");
        }
        return output.toString();
    }

    
}
