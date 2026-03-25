import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Bus extends Vehicle implements Updatable {
    private BusStop startStop;
    private BusStop endStop;
    private int inactiveTimer;

    @Override
    public void enterBusStop(BusStop busStop) {

    }

    public void rollNextStop() {

    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public boolean isInactive() throws NotImplementedException{
        throw new NotImplementedException("");
    }

    public void setStops(BusStop startStop, BusStop endStop) {

    }

    @Override
    public void crash(Lane lane) {
        super.crash(lane);
    }

    
}
