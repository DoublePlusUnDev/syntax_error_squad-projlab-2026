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
    private int laneCount;

    private float snowHeight = 0;
    private boolean iceDebris = false;
    private int icingProgress = 0;
    private boolean iced = false;
    private boolean vehicleBlock;
    private int saltedTimer;

    private static final float SNOW_COMPRESS_RATE = 0.02f;
    private static final int ICE_STEPS = 5; 
    private static final float SNOW_THRESHOLD = 0.2f;
    private static final float SNOW_REMOVED_BY_SALT = 0.01f;

    public Lane(String id, RoadSegment roadSegment, int laneCount){
        this.id = id;
        ObjectRegistry.register(id, this);
        this.roadSegment = roadSegment;
        this.laneCount = laneCount;
        GameLogic.getInstance().registerUpdatable(this);
    }

    public void addSnow(float snowLevel) {
        snowHeight += snowLevel;
    }

    public float getSnow(){
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
        if (snowHeight >= SNOW_COMPRESS_RATE){
            snowHeight -= SNOW_COMPRESS_RATE;
            icingProgress++;
        }

        if (icingProgress >= ICE_STEPS) {
            iced = true;
        }

        if (iced) {
            icingProgress = 0;
        }
    }

    public void crashOccured() {
        vehicleBlock = true;
    }

    public boolean willSlip() {
        boolean slip = iced;
        return slip;
    }

    public RoadSegment getSegment() {
        return roadSegment;
    }

    public int getCount() {
        return laneCount;
    }

    public void sweep() {
        roadSegment.sweep(this);
    }

    public void blow() {
        roadSegment.blow(this);
    }

    public void salt() {
        saltedTimer = 5; 
    }

    public void breakIce() {

        if (iced){
            iced = false;
            iceDebris = true;
        }
            
    }

    public void destroySnow() {
        snowHeight = 0;
    }

    public void destroyIce() {
        iced = false;
    }

    public boolean isSnowy() {
        return snowHeight > SNOW_THRESHOLD;
    }

    public boolean isBlocked() {
        return vehicleBlock;
    }

    public boolean isDebrisFilled() {
        return iceDebris;
    }

    public void setSnowHeight(float height) {
        snowHeight = height;
    }

    public void setIcingProgress(int progress) {
        icingProgress = progress;
    }

    public void setIced(boolean iced) {
        this.iced = iced;
    }

    public void setVehicleBlock(boolean vehicleBlock) {
        this.vehicleBlock = vehicleBlock;
    }

    public void setIceDebris(boolean iceDebris) {
        this.iceDebris = iceDebris;
    }

    public void setSaltedTimer(int saltedTimer) {
        this.saltedTimer = saltedTimer;
    }

    public void setGravelHeight(float gravelHeight) {
    }

    public int getIcingProgress() {
        return icingProgress;
    }

    public boolean isIced() {
        return iced;
    }

    public float getGravelHeight() {
        return 0;
    }


    @Override
    public void update() {
        if (saltedTimer > 0) {
            
            snowHeight = Math.max(0, snowHeight - SNOW_REMOVED_BY_SALT);

            saltedTimer--;

            if (saltedTimer == 0)
                destroyIce();
        }
    }

    @Override
    public void inspect() {
        Logger.logLine("Lane " + id + " details:");
        Logger.logLine("Snow Height: " + snowHeight);
        Logger.logLine("Icing Progress: " + icingProgress);
        Logger.logLine("Iced: " + (iced ? "yes" : "no"));
        Logger.logLine("Vehicle Block: " + (vehicleBlock ? "yes" : "no"));
        Logger.logLine("Ice Debris: " + (iceDebris ? "yes" : "no"));
    }
}
