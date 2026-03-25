import java.util.ArrayList;
import java.util.List;

public class Skeleton {
    static List<Runnable> tests = List.of(Skeleton::test1);

    static RoadNetwork testRoad;
    static Node node1;
    static Node node2;
    static Node node3;
    static RoadSegment road1;
    static RoadSegment road2;
    static SnowPlowPlayer snowPlowPlayer;
    static BusPlayer busPlayer;

    public static void main(String[] args) {
        

        System.out.println("Testing...");
        
        while (true) { 
            System.err.println("Select a testcase: (1-26) ");
            
            int testCase;
            try { 
                testCase = Integer.parseInt(TestUtil.scanner.nextLine());
            } catch (Exception e) {
                testCase = -1;
            }
            
        
            if (1 <= testCase && testCase <= 26){
                tests.get(testCase - 1).run();
            }
            else if (testCase == 27) {
                break;
            }
            else{
                System.out.println("Invalid testcase!");
            }
        }
        
        
    }

    static void init1(){
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        snowPlowPlayer = new SnowPlowPlayer(testRoad);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addRoadSegment(road1);
        testRoad.addSnowPlow(snowPlowPlayer.getSnowPlow());
    }

    static void test1() {
        init1();

        snowPlowPlayer.takeTurn();
    }
}