
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

    public boolean canEnter(Lane lane) {
        TestUtil.enterFunction("Vehicle:canEnter()");

        if (lane.isBlocked()){
            TestUtil.exitFunction("cant enter lane is blocked");
            return false;
        }

        if (lane.isSnowy()){
            TestUtil.exitFunction("cant enter lane is snowy");
            return false;
        }

        if (lane.isDebrisFilled()){
            TestUtil.exitFunction("cant enter lane is full of debris");
            return false;
        }

        TestUtil.exitFunction("lane can be entered");
        return true;
    }

    public boolean canSlip() throws NotImplementedException {
        throw new NotImplementedException("");
    }
}
