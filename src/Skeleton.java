import java.util.List;

public class Skeleton {
    static List<Runnable> tests = List.of(Skeleton::test1, Skeleton::test2, Skeleton::test3, Skeleton::test4, Skeleton::test5, 
        Skeleton::test6, Skeleton::test7, Skeleton::test8, Skeleton::test9, Skeleton::test10, Skeleton::test11, Skeleton::test12, Skeleton::test13, Skeleton::test14, 
        Skeleton::test15, Skeleton::test16, Skeleton::test17, Skeleton::test18, Skeleton::test19);

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

    static void init2(){
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        busPlayer = new BusPlayer(testRoad);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addRoadSegment(road1);
        testRoad.placeBus(busPlayer.getBus());
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

    static void init4(){
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new BusStop();
        node3 = new Node();
        road1 = new RoadSegment(2, node1, node2);
        road2 = new Bridge(2, node2, node3);
        busPlayer = new BusPlayer(testRoad);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.placeBus(busPlayer.getBus());
    }

    static void addPlowHead(PlowHead plowHead) {
        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        plowHead.inventory = snowPlowPlayer.inventory;
        snowPlow.equip(plowHead);
    }

    /**
     * UC1 Snowplow Movement Successful
     */
    static void test1() {
        TestUtil.turnOffLogging();
        init3();
        TestUtil.turnOnLogging();

        snowPlowPlayer.takeTurn();
    }

    /**
     * UC2 Snowplow Movement Unsuccessful
     */
    static void test2() {
        TestUtil.turnOffLogging();
        init1();
        TestUtil.turnOnLogging();

        snowPlowPlayer.takeTurn();
    }

    /**
     * UC3 Sweeper Head Use Middle of the Road
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
     * UC4 Sweeper Head Use on Right Side of the Road
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
     * UC5 Sweeper Head Use on Right Side of a Bridge
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
     * UC6 Blower Head Use on RoadSegment
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
     * UC7 Blower Head Use on Middle of Bridge
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
     * UC8 Blower Head Use on Right Side of Bridge
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
     * UC9 Ice Breaker Head Use on Icy Road
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
     * UC10 Ice Breaker Head Use on Iceless Road
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
     * UC11 Salter Head Use Successful
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
     * UC12 Salter Head Use Unsuccessful
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
     * UC13 Dragon Head Use Successful
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
     * UC14 Dragon Head Use Unsuccessful
     */
    static void test14() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new DragonHead());
        TestUtil.turnOnLogging();

        snowPlow.enter(road1.lanes.get(0));
    }
 
    /**
     * UC15 Head Switch
     */
    static void test15() {
        TestUtil.turnOffLogging();
        init1();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(new DragonHead());
        TestUtil.turnOnLogging();

        snowPlow.equip(new SweeperHead());
    }

    /**
     * UC16 Bus Movement Successful
     */
    static void test16() {
        TestUtil.turnOffLogging();
        init4();
        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }

    /**
     * UC17 Bus Movement Unsuccessful
     */
    static void test17() {
        TestUtil.turnOffLogging();
        init4();
        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }

    /**
     * UC 18 Bus Slips
     */
    static void test18() {
        TestUtil.turnOffLogging();
        init4();
        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }

    /**
     * UC 19 Bus Enters Busstop
     */
    static void test19() {
        TestUtil.turnOffLogging();
        init4();
        TestUtil.turnOnLogging();

        node2.accept(busPlayer.getBus());
    }
}
