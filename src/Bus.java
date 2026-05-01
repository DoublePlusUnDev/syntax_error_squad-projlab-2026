
/**
 * A bus vehicle, meant to be controlled by the player.
 * Has two stops, it must move between them in an alternating fashion, will earn money on entering it's target.
 * Can slip, cannot traverse deep snow or ice debris. 
 */
public class Bus extends Vehicle implements Updatable {
    private BusStop startStop;
    private BusStop endStop;
    private int inactiveTimer;

    private static final int CRASH_TIMEOUT = 10;

    public Bus(String id){
        super(id);
    }

    @Override
    public void enterBusStop(BusStop busStop) {
        if (busStop == endStop) {
            BusStop temp = startStop;
            startStop = endStop;
            endStop = temp;
        }
    }

    @Override
    public void update() {
        if (inactiveTimer > 0)
            inactiveTimer--;
    }

    public boolean isInactive() {
        return inactiveTimer > 0;
    }

    public void setStops(BusStop startStop, BusStop endStop) {
        this.startStop = startStop;
        this.endStop = endStop;
    }

    @Override
    public void crash(Lane lane) {
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
