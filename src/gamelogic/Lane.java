package gamelogic;

import java.util.ArrayList;
import java.util.List;
import utils.Logger;
import utils.ObjectRegistry;
import utils.RandomGenerator;

/**
 * Represents a single lane of the road.
 * 
 * Tracks the lane's condition including snow accumulation, ice formation, and debris.
 * Manages vehicle interactions (collisions, driving over snow/ice) and maintenance actions
 * (sweeping, blowing, salting, ice breaking). Reports slip hazards and can be cleaned
 * via the parent RoadSegment when multiple lanes are involved.
 */
public class Lane implements Updatable, Inspectable {
    private List<Runnable> onChangeCallback = new ArrayList<>();

    // Lane identification
    public String id;
    private RoadSegment roadSegment;
    // 0-based index of this lane within its parent RoadSegment
    private int laneIndex;

    // Snow and ice state
    private float snowHeight = 0;
    private int icingProgress = 0;
    private boolean iced = false;
    
    // Hazard state
    private boolean iceDebris = false;
    private boolean vehicleBlock = false;
    
    // Maintenance state
    private int saltedTimer = 0;
    private float gravelHeight = 0;

    //Vehicles
    private List<Vehicle> vehicles = new ArrayList<>();

    // Physical constants
    private static final float SNOW_COMPRESS_RATE = 0.02f;
    private static final int ICE_STEPS = 5;
    private static final float SNOW_THRESHOLD = 0.2f;
    private static final float SNOW_REMOVED_BY_SALT = 0.01f;
    private static final float SLIP_CHANCE = 0.5f;
    private static final float GRAVEL_OVER_SNOW_BUFFER = 0.1f;

    // ==================== Initialization ====================

    public Lane(String id, RoadSegment roadSegment, int laneIndex) {
        this.id = id;
        ObjectRegistry.register(id, this);
        this.roadSegment = roadSegment;
        this.laneIndex = laneIndex;
        GameLogic.getInstance().registerUpdatable(this);
    }

    // ==================== State Queries ====================

    /**
     * Checks if the lane has enough snow to be considered snowy.
     * @return true if snow height exceeds the threshold
     */
    public boolean isSnowy() {
        return snowHeight > SNOW_THRESHOLD;
    }

    /**
     * Checks if a vehicle is blocking this lane (e.g., from a crash).
     * @return true if the lane is blocked
     */
    public boolean isBlocked() {
        return vehicleBlock;
    }

    /**
     * Checks if the lane has ice debris from broken ice.
     * @return true if ice debris is present
     */
    public boolean isDebrisFilled() {
        return iceDebris;
    }

    /**
     * Checks if the lane is currently iced over.
     * @return true if the lane is iced
     */
    public boolean isIced() {
        return iced;
    }

    /**
     * Determines if a vehicle will slip on this lane.
     * @return true if the lane is iced (slip hazard)
     */
    public boolean willSlip() {
        return iced && snowHeight > gravelHeight && RandomGenerator.decide(SLIP_CHANCE);
    }

    public boolean isOccupied() {
        return !vehicles.isEmpty();
    }

    // ==================== State Modifiers ====================

    /**
     * Adds snow to the lane, accumulating over multiple calls.
     * @param snowLevel the amount of snow to add
     */
    public void addSnow(float snowLevel) {
        setSnowHeight(snowHeight + snowLevel);
    }

    public void addGravel(float gravelLevel) {
        setGravelHeight(gravelHeight + gravelLevel);
    }

    public void throwGravel() {
        setGravelHeight(snowHeight + GRAVEL_OVER_SNOW_BUFFER);
    }

    /**
     * Simulates a vehicle driving over the lane.
     * Compresses snow and advances icing progress. Once fully iced, the lane
     * remains iced but resets icing progress with each pass.
     */
    public void driveOver() {
        if (snowHeight >= SNOW_COMPRESS_RATE) {
            setSnowHeight(snowHeight - SNOW_COMPRESS_RATE);
            setIcingProgress(icingProgress + 1);
        }

        if (icingProgress >= ICE_STEPS) {
            setIced(true);
        }

        if (iced) {
            setIcingProgress(0);
        }
    }

    /**
     * Records that a crash has occurred on this lane, blocking it.
     */
    public void crashOccured() {
        setVehicleBlock(true);
    }

    /**
     * Applies salt to the lane. Reduces snow over time and eventually destroys ice.
     */
    public void salt() {
        setSaltedTimer(5);
    }

    /**
     * Breaks the ice on the lane, converting it to debris.
     */
    public void breakIce() {
        if (iced) {
            setIced(false);
            setIceDebris(true);
            Logger.logLine("LANE [" + id + "] ICE BROKEN");
        }
    }

    /**
     * Completely removes all snow from the lane.
     */
    public void destroySnow() {
        setSnowHeight(0);
        Logger.logLine("LANE [" + id + "] SNOW DESTROYED");
    }

    public void destroyGravel() {
        setGravelHeight(0);
        Logger.logLine("LANE [" + id + "] GRAVEL DESTROYED");
    }

    /**
     * Removes the ice from the lane without creating debris.
     */
    public void destroyIce() {
        setIced(false);
        Logger.logLine("LANE [" + id + "] ICE DESTROYED");
    }

    /**
     * Delegates sweeping to the parent road segment.
     */
    public void sweep() {
        roadSegment.sweep(this);
    }

    /**
     * Delegates blowing to the parent road segment.
     */
    public void blow() {
        roadSegment.blow(this);
    }

    // ==================== Accessors ====================

    public float getSnow() {
        return snowHeight;
    }

    public float getGravel() {
        return gravelHeight;
    }

    public int getIcingProgress() {
        return icingProgress;
    }

    public float getGravelHeight() {
        return gravelHeight;
    }

    public RoadSegment getSegment() {
        return roadSegment;
    }

    /**
     * Returns the 0-based index of this lane inside its RoadSegment.
     */
    public int getCount() {
        return laneIndex;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    // ==================== Mutators (for restoration/testing) ====================

    public void setSnowHeight(float height) {
        if (height == this.snowHeight)
            return;

        Logger.logLine("LANE [" + id + "] CHANGED [snowHeight] FROM [" + this.snowHeight + "] TO [" + height + "]");
        this.snowHeight = height;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setIcingProgress(int progress) {
        if (progress == this.icingProgress)
            return;
        
        Logger.logLine("LANE [" + id + "] CHANGED [icingProgress] FROM [" + this.icingProgress + "] TO [" + progress + "]");
        this.icingProgress = progress;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setIced(boolean iced) {
        if (iced == this.iced)
            return;
        
        Logger.logLine("LANE [" + id + "] CHANGED [iced] FROM [" + this.iced + "] TO [" + iced + "]");
        this.iced = iced;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setVehicleBlock(boolean vehicleBlock) {
        if (vehicleBlock == this.vehicleBlock)
            return;
        
        Logger.logLine("LANE [" + id + "] CHANGED [vehicleBlock] FROM [" + this.vehicleBlock + "] TO [" + vehicleBlock + "]");
        this.vehicleBlock = vehicleBlock;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setIceDebris(boolean iceDebris) {
        if (iceDebris == this.iceDebris)
            return;
        
        Logger.logLine("LANE [" + id + "] CHANGED [iceDebris] FROM [" + this.iceDebris + "] TO [" + iceDebris + "]");
        this.iceDebris = iceDebris;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setSaltedTimer(int saltedTimer) {
        if (saltedTimer == this.saltedTimer)
            return;
        
        Logger.logLine("LANE [" + id + "] CHANGED [saltedTimer] FROM [" + this.saltedTimer + "] TO [" + saltedTimer + "]");
        this.saltedTimer = saltedTimer;
        onChangeCallback.forEach(Runnable::run);
    }

    public void setGravelHeight(float gravelHeight) {
        if (gravelHeight == this.gravelHeight)
            return; 

        Logger.logLine("LANE [" + id + "] CHANGED [gravelHeight] FROM [" + this.gravelHeight + "] TO [" + gravelHeight + "]");
        this.gravelHeight = gravelHeight;
        onChangeCallback.forEach(Runnable::run);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        onChangeCallback.forEach(Runnable::run);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
        onChangeCallback.forEach(Runnable::run);
    }

    public String getId() {
        return id;
    }

    public void addOnChangeListener(Runnable callback) {
        onChangeCallback.add(callback);
    }

    // ==================== Lifecycle ====================

    /**
     * Updates the lane state each game tick.
     * Reduces snow when salted and eventually destroys ice after salt timer expires.
     */
    @Override
    public void update() {
        if (saltedTimer > 0) {
            snowHeight = Math.max(0, snowHeight - SNOW_REMOVED_BY_SALT);
            saltedTimer--;

            if (saltedTimer == 0) {
                destroyIce();
            }
        }
    }

    /**
     * Logs detailed information about the lane's current state.
     */
    @Override
    public void inspect() {
        Logger.logLine("Lane " + id + " details:");
        Logger.logLine("Snow Height: " + snowHeight);
        Logger.logLine("Gravel Height: " + gravelHeight);
        Logger.logLine("Icing Progress: " + icingProgress);
        Logger.logLine("Iced: " + (iced ? "yes" : "no"));
        Logger.logLine("Vehicle Block: " + (vehicleBlock ? "yes" : "no"));
        Logger.logLine("Ice Debris: " + (iceDebris ? "yes" : "no"));
        Logger.logLine("Salted Timer: " + saltedTimer);
        Logger.logLine("Vehicles on lane: " + vehicles.size());
        for (Vehicle vehicle : vehicles) {
            Logger.logLine("- " + vehicle.id);
        }
    }
}
