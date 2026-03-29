import java.util.List;

public class Skeleton {
    static List<Runnable> tests = List.of(Skeleton::test1, Skeleton::test2, Skeleton::test3, Skeleton::test4, Skeleton::test5, 
        Skeleton::test6, Skeleton::test7, Skeleton::test8, Skeleton::test9, Skeleton::test10, Skeleton::test11, Skeleton::test12, Skeleton::test13, Skeleton::test14, 
        Skeleton::test15, Skeleton::test16, Skeleton::test17, Skeleton::test18, Skeleton::test19, Skeleton::test20, Skeleton::test21, Skeleton::test22, Skeleton::test23,
        Skeleton::test24, Skeleton::test25, Skeleton::test26);

    static RoadNetwork testRoad;
    static Node node1;
    static Node node2;
    static Node node3;
    static Node node4;
    static RoadSegment road1;
    static RoadSegment road2;
    static RoadSegment road3;
    static SnowPlowPlayer snowPlowPlayer;
    static BusPlayer busPlayer;
    static Vehicle collisionVehicle;
    static SweeperHead sweeperHead;
    static BlowerHead blowerHead;
    static IceBreakerHead iceBreakerHead;
    static SalterHead salterHead;
    static DragonHead dragonHead;

    public static void main(String[] args) {
        System.out.println("Testing...");
        
        while (true) { 

            System.out.println();
            System.out.println("Select a testcase: (1-26)");
            System.out.print("> ");
            
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

    /**
     * Initializes two nodes with a single lane road between them.
     * There is a snowplow on the road. It's unable to move.
     */
    static void init1(){
        System.out.println("[INIT] SingleRoadSnowplowInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        snowPlowPlayer = new SnowPlowPlayer(testRoad);

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        snowPlow.equip(sweeperHead);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addRoadSegment(road1);
        testRoad.placeSnowPlow(snowPlowPlayer.getSnowPlows().get(0));
        snowPlow.location = road1.lanes.get(0);
    }

    /**
     * Initializes two nodes with a single lane road between them.
     * There is a bus on the road. It's unable to move.
     */
    static void init2(){
        System.out.println("[INIT] SingleRoadBusInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        busPlayer = new BusPlayer(testRoad);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addRoadSegment(road1);
        testRoad.placeBus(busPlayer.getBus());
        busPlayer.getBus().location = road1.lanes.get(0);
    }

    /**
     * Initalizes three nodes in a row, with a road and a bridge connecting them.
     * There is a snowplow on the first lane of the road.
     */
    static void init3(){
        System.out.println("[INIT] RoadBridgeSnowplowInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new Node();
        node3 = new Node();
        road1 = new RoadSegment(2, node1, node2);
        road2 = new Bridge(2, node2, node3);
        snowPlowPlayer = new SnowPlowPlayer(testRoad);
        
        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        snowPlow.equip(new SweeperHead());

        sweeperHead = new SweeperHead();
        blowerHead = new BlowerHead();
        iceBreakerHead = new IceBreakerHead();
        salterHead = new SalterHead();
        dragonHead = new DragonHead();

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.placeSnowPlow(snowPlow);

        snowPlow.location = road1.lanes.get(0);
    }

    /**
     * Initalizes three nodes in a row, with two roads connecting them.
     * There is a bus on the first lane of the road.
     */
    static void init4(){
        System.out.println("[INIT] BusRouteWithStopInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new BusStop();
        node3 = new Node();
        road1 = new RoadSegment(2, node1, node2);
        road2 = new RoadSegment(2, node2, node3);
        busPlayer = new BusPlayer(testRoad);

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.placeBus(busPlayer.getBus());

        busPlayer.getBus().location = road1.lanes.get(0);
    }

    /**
     * Initializes four nodes in a line, connected by roadsegments.
     * There is a bus on the first segment, there is a snowplow on the third.
     * Intended for crash testing.
     */
    static void init5(){
        System.out.println("[INIT] CollisionBusSnowPlowInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new BusStop();
        node3 = new Node();
        node4 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        road2 = new RoadSegment(1, node2, node3);
        road3 = new RoadSegment(1, node3, node4);
        busPlayer = new BusPlayer(testRoad);
        collisionVehicle = new SnowPlow();

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addNode(node4);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.addRoadSegment(road3);
        testRoad.placeBus(busPlayer.getBus());
        testRoad.placeSnowPlow((SnowPlow)collisionVehicle);

        busPlayer.getBus().location = road1.lanes.get(0);
        collisionVehicle.location = road3.lanes.get(0);
    }

    /**
     * Initializes four nodes in a line, connected by roadsegments.
     * There is a bus on the first segment, there is a bus on the third.
     * Intended for crash testing.
     */
    static void init6(){
        System.out.println("[INIT] CollisionBusBusInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new BusStop();
        node3 = new Node();
        node4 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        road2 = new RoadSegment(1, node2, node3);
        road3 = new RoadSegment(1, node3, node4);
        busPlayer = new BusPlayer(testRoad);
        collisionVehicle = new Bus();

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addNode(node4);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.addRoadSegment(road3);
        testRoad.placeBus(busPlayer.getBus());
        testRoad.placeBus((Bus)collisionVehicle);

        busPlayer.getBus().location = road1.lanes.get(0);
        collisionVehicle.location = road3.lanes.get(0);
    }

    /**
     * Initializes four nodes in a line, connected by roadsegments.
     * There is a bus on the first segment, there is a car on the third.
     * Intended for crash testing.
     */
    static void init7(){
        System.out.println("[INIT] CollisionBusCarInit");
        testRoad = new RoadNetwork();

        node1 = new Node();
        node2 = new BusStop();
        node3 = new Node();
        node4 = new Node();
        road1 = new RoadSegment(1, node1, node2);
        road2 = new RoadSegment(1, node2, node3);
        road3 = new RoadSegment(1, node3, node4);
        busPlayer = new BusPlayer(testRoad);
        collisionVehicle = new Car();

        testRoad.addNode(node1);
        testRoad.addNode(node2);
        testRoad.addNode(node3);
        testRoad.addNode(node4);
        testRoad.addRoadSegment(road1);
        testRoad.addRoadSegment(road2);
        testRoad.addRoadSegment(road3);
        testRoad.placeBus(busPlayer.getBus());
        testRoad.placeCar((Car)collisionVehicle);

        busPlayer.getBus().location = road1.lanes.get(0);
        collisionVehicle.location = road3.lanes.get(0);
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
        addPlowHead(sweeperHead);
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
        addPlowHead(sweeperHead);
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
        addPlowHead(sweeperHead);
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
        addPlowHead(blowerHead);
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
        addPlowHead(blowerHead);
        TestUtil.turnOnLogging();

        snowPlow.enter(road2.lanes.get(0));
    }

    /**
     * UC8 Blower Head Use on Right Side of Bridge
     */
    static void test8() {
        TestUtil.turnOffLogging();
        init3();

        SnowPlow snowPlow = snowPlowPlayer.getSnowPlows().get(0);
        addPlowHead(blowerHead);
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
        addPlowHead(iceBreakerHead);
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
        addPlowHead(iceBreakerHead);
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
        addPlowHead(salterHead);
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
        addPlowHead(salterHead);
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
        addPlowHead(dragonHead);
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
        addPlowHead(dragonHead);
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
        init2();
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

    /**
     * UC 20 Snowplow Lane Change Successful
     */
    static void test20() {
        TestUtil.turnOffLogging();
        init3();
        TestUtil.turnOnLogging();

        testRoad.changeLane(snowPlowPlayer.getSnowPlows().get(0), road1.lanes.get(0));
    }

    /**
     * UC 21 Snowplow Lane Change Unsuccessful
     */
    static void test21() {
        TestUtil.turnOffLogging();
        init3();
        TestUtil.turnOnLogging();

        testRoad.changeLane(snowPlowPlayer.getSnowPlows().get(0), road1.lanes.get(0));
    }

    /**
     * UC 22 Purchase Successful
     */
    static void test22() {
        TestUtil.turnOffLogging();
        init3();
        DragonHead dragonHead = new DragonHead();
        TestUtil.turnOnLogging();

        
        dragonHead.buy(snowPlowPlayer.inventory);
    }

    /**
     * UC 23 Purchase Unsuccessful
     */
    static void test23() {
        TestUtil.turnOffLogging();
        init3();
        DragonHead dragonHead = new DragonHead();
        TestUtil.turnOnLogging();

        
        dragonHead.buy(snowPlowPlayer.inventory);
    }

    /**
     * UC 24 Collision of a Bus With a Snowplow
     */
    static void test24() {
        TestUtil.turnOffLogging();
        init5();

        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }

    /**
     * UC 25 Collision of a Bus With a Bus
     */
    static void test25() {
        TestUtil.turnOffLogging();
        init6();

        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }

    /**
     * UC 26 Collision of a Bus With a Car
     */
    static void test26() {
        TestUtil.turnOffLogging();
        init7();

        TestUtil.turnOnLogging();

        busPlayer.takeTurn();
    }
}
