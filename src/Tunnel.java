/**
 * A type of road that is immune to snowfall.
 */
public class Tunnel extends RoadSegment {

    public Tunnel(int laneCount, Node startPoint, Node endPoint) {
        super(laneCount, startPoint, endPoint);
    }

    @Override
    public void addSnow(int snowLevel) {
        
    }
}
