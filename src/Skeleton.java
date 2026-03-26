import java.util.List;

public class Skeleton {
    static List<Runnable> tests = List.of(Skeleton::test1, Skeleton::test2, Skeleton::test3, Skeleton::test4, Skeleton::test5, 
        Skeleton::test6, Skeleton::test7, Skeleton::test8, Skeleton::test9, Skeleton::test10, Skeleton::test11, Skeleton::test12, Skeleton::test13, Skeleton::test14);

    static RoadNetwork testRoad;
    static Node node1;
    static Node node2;
    static Node node3;
    static RoadSegment road1;
    static RoadSegment road2;
    static SnowPlowPlayer snowPlowPlayer;
    static BusPlayer busPlayer;
    static SweeperHead sweeperHead;
    static BlowerHead blowerHead;
    static IceBreakerHead iceBreakerHead;
    static SalterHead salterHead;
    static DragonHead dragonHead;

    public static void main(String[] args) {
        System.out.println("Testing...");
        
        while (true) { 
            System.err.println("Select a testcase: (1-26) ");
            
            int testCase;
            try { 
                testCase = Integer.parseInt(TestUtil.scanner.nextLine());
            } catch (NumberFormatException e) {
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
        snowPlowPlayer.getSnowPlows().get(0).equip(new SweeperHead());

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addRoadSegment(road1);
        testRoad.placeSnowPlow(snowPlowPlayer.getSnowPlows().get(0));
    }

    static void init3(){
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        node3 = new Node();
        road1 = new RoadSegment(2, node1, node2);
        road2 = new Bridge(2, node2, node3);
        snowPlowPlayer = new SnowPlowPlayer(testRoad);
        snowPlowPlayer.getSnowPlows().get(0).equip(new SweeperHead());

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.placeSnowPlow(snowPlowPlayer.getSnowPlows().get(0));
    }

    static void addPlowHead(PlowHead plowHead) {
        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        plowHead.inventory = snowPlowPlayer.inventory;
        snowPlow.equip(plowHead);
    }

    /**
     * Successful snowplow movement
     */
    static void test1() {
        TestUtil.turnOffLogging();
        init3();
        TestUtil.turnOnLogging();

        snowPlowPlayer.takeTurn();
    }

    /**
     * Unsuccesful snowplow movement
     */
    static void test2() {
        TestUtil.turnOffLogging();
        init1();
        TestUtil.turnOnLogging();

        snowPlowPlayer.takeTurn();
    }

    /**
     * Sweeper head middle of the road
     */
    static void test3() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new SweeperHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Sweeper head right side of the road
     */
    static void test4() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new SweeperHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Sweeper head right side of the bridge
     */
    static void test5() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new SweeperHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Blower head road
     */
    static void test6() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new BlowerHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Blower head middle of the bridge
     */
    static void test7() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new BlowerHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Blower head right side if the bridge
     */
    static void test8() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new BlowerHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }
 
    /**
     * Ice breaker head on icy road
     */
    static void test9() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new IceBreakerHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Ice breaker head on iceless road
     */
    static void test10() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new IceBreakerHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }
 
    /**
     * Salter head successful
     */
    static void test11() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new SalterHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Salter head unsuccesful
     */
    static void test12() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new SalterHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Dragon head successful
     */
    static void test13() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new DragonHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }

    /**
     * Dragon head unsuccesful
     */
    static void test14() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new DragonHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }
 
}