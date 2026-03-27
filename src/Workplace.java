import java.util.List;

/**
 * Special type of node the vehicle can enter. Acts as a workplace
 * and will invoke the vehicle's correspondig callback on enter.
 * 
 * Will attempt spawn the car back a few turns after it entered the workplace.
 */
public class Workplace extends Node implements Updatable {
    List<Car> restingCars;

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void accept(Vehicle vehicle) {
        TestUtil.enterFunction("Workplace:accept()");
        
        vehicle.enterWorkPlace(this);

        TestUtil.exitFunction("workplace accepted vehicle");
    }
    
    
}
