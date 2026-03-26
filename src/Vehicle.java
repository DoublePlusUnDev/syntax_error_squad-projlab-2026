
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
        System.out.println("Vehicle:canEnter");
        return TestUtil.askUserYesNo("Can the vehicle enter the lane?");
    }

    public boolean canSlip() throws NotImplementedException {
        throw new NotImplementedException("");
    }
}
