
/**
 * A bus vehicle, meant to be controlled by the player.
 * Has two stops, it must move between them in an alternating fashion, will earn money on entering it's target.
 * Can slip, cannot traverse deep snow or ice debris. 
 */
public class Bus extends Vehicle implements Updatable {
    private static final int CRASH_TIMEOUT = 10;

    private BusStop startStop;
    private BusStop endStop;
    private int inactiveTimer;

    public Bus(String id) {
        super(id);
    }

    // Getters

    /**
     * Checks if this bus is currently inactive (crashed or in recovery).
     *
     * @return true if the bus is inactive
     */
    public boolean isInactive() {
        return inactiveTimer > 0;
    }

    // Setters

    /**
     * Sets both the start and end stops for this bus.
     *
     * @param startStop the starting bus stop
     * @param endStop the ending bus stop
     */
    public void setStops(BusStop startStop, BusStop endStop) {
        this.startStop = startStop;
        this.endStop = endStop;
    }

    /**
     * Sets the start stop for this bus.
     *
     * @param startStop the starting bus stop
     */
    public void setStartStop(BusStop startStop) {
        this.startStop = startStop;
    }

    /**
     * Sets the end stop for this bus.
     *
     * @param endStop the ending bus stop
     */
    public void setEndStop(BusStop endStop) {
        this.endStop = endStop;
    }

    /**
     * Sets the inactive timer duration.
     *
     * @param time the timer duration in update cycles
     */
    public void setInactiveTimer(int time) {
        this.inactiveTimer = time;
    }

    // Overridden Methods

    @Override
    public void update() {
        if (inactiveTimer > 0)
            inactiveTimer--;
    }

    /**
     * Handles the bus entering a bus stop. If the bus enters its target stop, it will earn money and switch targets.
     */
    @Override
    public void enterBusStop(BusStop busStop) {
        if (busStop == endStop) {
            BusStop temp = startStop;
            startStop = endStop;
            endStop = temp;
        }
    }

    @Override
    public void crash(Lane lane) {
        super.crash(lane);
        inactiveTimer = CRASH_TIMEOUT; 
    }

    @Override
    public boolean canSlip() {
        return true;
    }

    @Override
    public void inspect() {
        Logger.logLine("Bus " + id + " details:");
        Logger.logLine("Location: " + (location != null ? location.id : "none"));
        Logger.logLine("Inactive: " + (isInactive() ? "yes" : "no"));
    }
}
