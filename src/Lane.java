
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
        TestUtil.enterFunction("Lane:addSnow()");
        TestUtil.exitFunction("snow added");
    }

    public float getSnow(){
        TestUtil.enterFunction("Lane:getSnow()");
        TestUtil.exitFunction("snow level");
        return snowHeight;
    }

    public void driveOver() {
        TestUtil.enterFunction("Lane:driveOver()");
        TestUtil.exitFunction("driven over");
    }

    public void crashOccured() {
        TestUtil.enterFunction("Lane:crashOccured()");
        TestUtil.exitFunction("road blocked");
    }

    public boolean willSlip() {
        TestUtil.enterFunction("Lane:willSlip()");
        boolean slip = TestUtil.askUserYesNo("Will the lane slip?");
        TestUtil.exitFunction(String.valueOf(slip));
        return slip;
    }

    public RoadSegment getSegment() {
        TestUtil.enterFunction("Lane:getSegment()");
        TestUtil.exitFunction("road segment");
        return roadSegment;
    }

    public void sweep() {
        TestUtil.enterFunction("Lane:sweep()");
        roadSegment.sweep(this);
        TestUtil.exitFunction("lane swept");
    }

    public void blow() {
        TestUtil.enterFunction("Lane:blown()");
        roadSegment.sweep(this);
        TestUtil.exitFunction("lane blown");
    }

    public void salt() {
        TestUtil.enterFunction("Lane:salt()");
        TestUtil.exitFunction("lane salted");
    }

    public void breakIce() {
        TestUtil.enterFunction("Lane:breakIce()");

        boolean ice = TestUtil.askUserYesNo("Is there ice to break");

        if (ice)
            TestUtil.exitFunction("ice broken");
        else
            TestUtil.exitFunction("no ice to break ");
    }

    public void destroySnow() {
        TestUtil.enterFunction("Lane:destroySnow()");
        TestUtil.exitFunction("snow destroyed");
    }

    public void destroyIce() {
        TestUtil.enterFunction("Lane:destroyIce()");
        TestUtil.exitFunction("ice destroyed");
    }

    public boolean isSnowy() {
        TestUtil.enterFunction("Lane:isSnowy()");
        boolean input = TestUtil.askUserYesNo("Is the lane snowy?");
        TestUtil.exitFunction(String.valueOf(input));
        return input;
    }

    public boolean isBlocked() {
        TestUtil.enterFunction("Lane:isBlocked()");
        boolean input = TestUtil.askUserYesNo("Is the lane blocked?");
        TestUtil.exitFunction(String.valueOf(input));
        return input;
    }

    public boolean isDebrisFilled() {
        TestUtil.enterFunction("Lane:isDebrisFilled()");
        boolean input = TestUtil.askUserYesNo("Is the lane debris filled?");
        TestUtil.exitFunction(String.valueOf(input));
        return input;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
