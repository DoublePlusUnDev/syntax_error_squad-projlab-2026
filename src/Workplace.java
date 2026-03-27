public class Workplace extends Node implements Updatable {

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
