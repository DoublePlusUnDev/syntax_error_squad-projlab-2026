public class Car extends Vehicle {

    @Override
    public void crash(Lane lane) {
        TestUtil.enterFunction("Car:crash(lane)");
        lane.crashOccured();
        TestUtil.exitFunction("car crashed");
    }
    
    
}
