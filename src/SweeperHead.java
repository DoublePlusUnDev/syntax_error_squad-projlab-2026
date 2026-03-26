public class SweeperHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        System.out.println("SweeperHead:clean(lane)");
        lane.sweep();
    }
    
}
