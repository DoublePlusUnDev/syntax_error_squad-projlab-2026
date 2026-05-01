/**
 * Represents a single lane of the road.
 * Keeps track of how snowy it is, how far it's icing has progressed,
 * whether it has ICE, debris and if a vehicle is blocking it. Also handles salting progress.
 * Reports whether a given vehicle will or won't slip on it. Can be cleaned in various ways, 
 * when other lanes are included in the process it calls back to the roadsegment containing the lane.
 */
public class Lane implements Updatable, Inspectable {
    String id;

    private RoadSegment roadSegment;
    private float snowHeight = 0;
    private boolean iceDebris = false;
    private int icingProgress = 0;
    private boolean iced = false;
    private boolean vehicleBlock;
    private int saltedTimer;

    private static final float snowCompressRate = 0.02f;
    private static final int iceSteps = 5; 
    private static final float snowThreshold = 0.2f;
    private static final float snowRemovedBySalt = 0.01f;

    public Lane(String id, RoadSegment roadSegment){
        this.id = id;
        ObjectRegistry.register(id, this);
        this.roadSegment = roadSegment;
        GameLogic.getInstance().registerUpdatable(this);
    }

    public void addSnow(float snowLevel) {
        TestUtil.enterFunction("Lane:addSnow()");
        snowHeight += snowLevel;
        TestUtil.exitFunction("snow added");
    }

    public float getSnow(){
        TestUtil.enterFunction("Lane:getSnow()");
        TestUtil.exitFunction("snow level");
        return snowHeight;
    }

    /**
     * Handles the logic for a vehicle driving over the lane.
     * If the lane is snowy, it will compress the snow and increase the icing progress.
     * If the icing progress reaches the threshold, the lane becomes iced.
     * If the lane is already iced, driving over it will reset the icing progress, but it will remain iced.
     */
    public void driveOver() {
        TestUtil.enterFunction("Lane:driveOver()");
        if (snowHeight >= snowCompressRate){
            snowHeight -= snowCompressRate;
            icingProgress++;
        }

        if (icingProgress >= iceSteps) {
            iced = true;
        }

        if (iced) {
            icingProgress = 0;
        }

        TestUtil.exitFunction("driven over");
    }

    public void crashOccured() {
        TestUtil.enterFunction("Lane:crashOccured()");
        vehicleBlock = true;
        TestUtil.exitFunction("road blocked");
    }

    public boolean willSlip() {
        TestUtil.enterFunction("Lane:willSlip()");

        boolean slip = iced;
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
        roadSegment.blow(this);
        TestUtil.exitFunction("lane blown");
    }

    public void salt() {
        TestUtil.enterFunction("Lane:salt()");
        saltedTimer = 5; 
        TestUtil.exitFunction("lane salted");
    }

    public void breakIce() {
        TestUtil.enterFunction("Lane:breakIce()");

        if (iced){
            iced = false;
            iceDebris = true;
            TestUtil.exitFunction("ice broken");
        }
        else
            TestUtil.exitFunction("no ice to break ");
    }

    public void destroySnow() {
        TestUtil.enterFunction("Lane:destroySnow()");
        snowHeight = 0;
        TestUtil.exitFunction("snow destroyed");
    }

    public void destroyIce() {
        TestUtil.enterFunction("Lane:destroyIce()");
        iced = false;
        TestUtil.exitFunction("ice destroyed");
    }

    public boolean isSnowy() {
        TestUtil.enterFunction("Lane:isSnowy()");
        
        TestUtil.exitFunction(String.valueOf(snowHeight > snowThreshold));
        return snowHeight > snowThreshold;
    }

    public boolean isBlocked() {
        TestUtil.enterFunction("Lane:isBlocked()");
        TestUtil.exitFunction(String.valueOf(vehicleBlock));
        return vehicleBlock;
    }

    public boolean isDebrisFilled() {
        TestUtil.enterFunction("Lane:isDebrisFilled()");
        TestUtil.exitFunction(String.valueOf(iceDebris));
        return iceDebris;
    }

    @Override
    public void update() {
        if (saltedTimer > 0) {
            
            snowHeight = Math.max(0, snowHeight - snowRemovedBySalt);

            saltedTimer--;

            if (saltedTimer == 0)
                destroyIce();
        }
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("Lane " + id + " details:\n");
        output.append("Snow Height: " + snowHeight + "\n");
        output.append("Icing Progress: " + icingProgress + "\n");
        output.append("Iced: " + (iced ? "yes" : "no") + "\n");
        output.append("Vehicle Block: " + (vehicleBlock ? "yes" : "no") + "\n");
        output.append("Ice Debris: " + (iceDebris ? "yes" : "no") + "\n");
        return output.toString();
    }
}
