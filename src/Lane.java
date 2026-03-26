
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Lane implements Updatable{
    private RoadSegment roadSegment;
    private float snowHeight;
    private boolean iceDebris;
    private int icingProgress;
    private boolean iced;
    private boolean vehicleBlock;
    private int saltedTimer;

    public Lane(RoadSegment roadSegment){
        this.roadSegment = roadSegment;
    }

    public void addSnow(float snowLevel) {

    }

    public void driveOver() {
        System.out.println("Lane:driveOver");
    }

    public void crashOccured() {

    }

    public boolean willSlip() throws NotImplementedException {
        throw new NotImplementedException("");
    }

    public RoadSegment getSegment() throws NotImplementedException{
        throw new NotImplementedException("");
    }

    public void sweep() {

    }

    public void blow() {

    }

    public void salt() {

    }

    public void breakIce() {

    }

    public void destroySnow() {
        
    }

    public void destroyIce() {

    }

    public boolean isSnowy() throws NotImplementedException{
        throw new NotImplementedException("");
    }

    public boolean isBlocked() throws NotImplementedException {
        throw new NotImplementedException("");
    }

    public boolean isDebrisFilled() throws NotImplementedException {
        throw new NotImplementedException("");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
