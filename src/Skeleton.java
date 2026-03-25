import java.util.Scanner;

public class Skeleton {

    RoadNetwork testRoad;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Testing...");
        
        while (true) { 
            System.err.println("Select a testcase: (1-26) ");
            
            int testCase;
            try {
                testCase = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                testCase = -1;
            }
            
        
            if (1 <= testCase && testCase <= 26){

            }
            else if (testCase == 27) {
                break;
            }
            else{
                System.out.println("Invalid testcase!");
            }
        }
        
        
    }

    void init(){
        testRoad = new RoadNetwork();

        Node node11 = new Node();
        Node node12 = new Node();
        RoadSegment road11 = new RoadSegment(1, node11, node12);
        SnowPlowPlayer snowPlow1 = new SnowPlowPlayer();

        testRoad.addNode(node11);
        testRoad.addNode(node12);
        testRoad.addRoadSegment(road11);
        testRoad.addSnowPlow(snowPlow1.getSnowPlow());

        
    }
}