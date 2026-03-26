public class SnowPlow extends Vehicle{

    private PlowHead plowHead;



    @Override
    public boolean canEnter(Lane lane) {
        TestUtil.enterFunction("SnowPlow:canEnter");

        boolean canEnter = TestUtil.askUserYesNo("Can the snowplow enter the lane?"); 

        TestUtil.exitFunction(String.valueOf(canEnter));
        return canEnter;
    }

    @Override
    public void enter(Lane lane) {
        System.out.println("SnowPlow:enter");
        
        
    }
    
    
}
