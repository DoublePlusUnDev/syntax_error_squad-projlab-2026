
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public abstract  class Vehicle {
    public void enter(Lane lane) {

    }

    public void enterApartment(Apartment apartment) {

    }

    public void enterWorkPlace(Workplace workplace) {

    }

    public void enterBusStop(BusStop busStop){

    }

    public void crash(Lane lane) {

    }

    public boolean canEnter(Lane lane) throws NotImplementedException {
        throw new NotImplementedException("");
    }

    public boolean canSlip() throws NotImplementedException {
        throw new NotImplementedException("");
    }
}
