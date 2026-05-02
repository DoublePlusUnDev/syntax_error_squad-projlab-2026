import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandInterpreter {
    public interface Command {
        void execute(Map<String, String> args);
        
    }

    Map<String, Command> commands = Map.ofEntries(
        Map.entry("/help", this::help),
        Map.entry("/random", this::random),
        Map.entry("/seed", this::seed),
        Map.entry("/logging", this::logging),
        Map.entry("/addnet", this::addNet),
        Map.entry("/generate", this::generate),
        Map.entry("/addnode", this::addNode),
        Map.entry("/addroad", this::addRoad),
        Map.entry("/modlane", this::modLane),
        Map.entry("/modplow", this::modPlow),
        Map.entry("/modbus", this::modBus),
        Map.entry("/modcar", this::modCar),
        Map.entry("/listroots", this::listRoots),
        Map.entry("inspect", this::inspect)
    );

    public CommandInterpreter() {

    }

    public void execute(String command) {
            String[] parts = command.split(" ");
            String commandName = parts[0];
            
            Map<String, String> args = new HashMap<>(); 
            for (int i = 1; i + 1 < parts.length; i+=2) {
                //System.out.println(parts[i] + ": " + parts[i+1]);
                String argName = parts[i];
                String argValue = parts[i+1];
                args.put(argName, argValue);
            }

            Command cmd = commands.get(commandName);
            if (cmd != null) {
                cmd.execute(args);
            } else {
                System.out.println("Unknown command: " + commandName);
                System.out.println("Run help for list of available commands.");
            }
    }
    
    public void help(Map<String, String> args) {
        File helpFile = new File("resources/help.txt");
        
        try (Scanner scanner = new Scanner(helpFile)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error reading help file: " + e.getMessage());
        }
    }

    public void random(Map<String, String> args) {
        if (!args.containsKey("-mode")) {
            System.out.println("Error: Missing -mode argument for random command.");
            return;
        }
        String mode = args.get("-mode");
        if (mode.equals("always")) {
            RandomGenerator.setMode(RandomGeneratorMode.ALWAYS);
        } else if (mode.equals("never")) {
            RandomGenerator.setMode(RandomGeneratorMode.NEVER);
        } else if (mode.equals("random")) {
            RandomGenerator.setMode(RandomGeneratorMode.RANDOM);
        } else {
            System.out.println("Error: Invalid mode for random command. Use always, never, or random.");
        }
    }

    public void seed(Map<String, String> args) {
        if (!args.containsKey("-seed")) {
            System.out.println("Error: Missing -seed argument for seed command.");
            return;
        }

        String seed = args.get("-seed");
        RandomGenerator.setSeed(seed);
    }

    public void logging(Map<String, String> args) {
        if (!args.containsKey("-enable")) {
            System.out.println("Error: Missing -enable argument for logging command.");
            return;
        }

        boolean enable = Boolean.parseBoolean(args.get("-enable"));
        Logger.setEnabled(enable);
    }

    public void addNet(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for addnet command.");
            return;
        }

        GameLogic.getInstance().roads.add(new RoadNetwork(args.get("-id")));
    }

    public void generate(Map<String, String> args) {
        RoadGenerationParameters params = RoadGenerationParameters.testParams;
        if (args.containsKey("nodeMin"))
            params.nodeMin = Integer.parseInt(args.get("nodeMin"));
        if (args.containsKey("nodeMax"))
            params.nodeMax = Integer.parseInt(args.get("nodeMax"));
        if (args.containsKey("mainLanes"))
            params.mainLanes = Integer.parseInt(args.get("mainLanes"));
        if (args.containsKey("smallNodesMin"))
            params.smallNodesMin = Integer.parseInt(args.get("smallNodesMin"));
        if (args.containsKey("smallNodesMax"))
            params.smallNodesMax = Integer.parseInt(args.get("smallNodesMax"));
        if (args.containsKey("smallNodeLanes"))
            params.smallNodeLanes = Integer.parseInt(args.get("smallNodeLanes"));
        if (args.containsKey("bigNodesMin"))
            params.bigNodesMin = Integer.parseInt(args.get("bigNodesMin"));
        if (args.containsKey("bigNodesMax"))
            params.bigNodesMax = Integer.parseInt(args.get("bigNodesMax"));
        if (args.containsKey("bigNodeLanes"))
            params.bigNodeLanes = Integer.parseInt(args.get("bigNodeLanes"));
        if (args.containsKey("busStopsMin"))
            params.busStopsMin = Integer.parseInt(args.get("busStopsMin"));
        if (args.containsKey("busStopsMax"))
            params.busStopsMax = Integer.parseInt(args.get("busStopsMax"));
        if (args.containsKey("workPlacesMin"))
            params.workPlacesMin = Integer.parseInt(args.get("workPlacesMin"));
        if (args.containsKey("workPlacesMax"))
            params.workPlacesMax = Integer.parseInt(args.get("workPlacesMax"));
        if (args.containsKey("apartsmentsMin"))
            params.apartsmentsMin = Integer.parseInt(args.get("apartsmentsMin"));
        if (args.containsKey("apartsmentsMax"))
            params.apartsmentsMax = Integer.parseInt(args.get("apartsmentsMax"));

        RoadNetwork roadNetwork = new RoadNetwork(args.get("-id"));
        roadNetwork.setGenerationParameters(params);
        roadNetwork.generate();
        GameLogic.getInstance().roads.add(roadNetwork);
    }

    public void addNode(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-net")) {
            System.out.println("Error: Missing arguments for addnode command. Required: -id, -net.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            System.out.println("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("busstop")) {
                net.addNode(new BusStop(args.get("-id")));
            }
            else if (type.equals("workplace")) {
                net.addNode(new Workplace(args.get("-id")));
            }
            else if (type.equals("apartment")) {
                net.addNode(new Apartment(args.get("-id")));
            }
            else {
                System.out.println("Error: Invalid node type. Use busstop, workplace, or apartment. Using default type of busstop.");
                net.addNode(new BusStop(args.get("-id")));
            }
        }
        else {
            net.addNode(new BusStop(args.get("-id")));
        }
    }

        

    public void addRoad(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-start") || !args.containsKey("-end")) {
            System.out.println("Error: Missing arguments for addroad command. Required: -id, -net, -start, -end.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            System.out.println("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        Node start = ObjectRegistry.get(args.get("-start"), Node.class);
        if (start == null) {
            System.out.println("Error: Start node with id " + args.get("-start") + " does not exist.");
            return;
        }

        Node end = ObjectRegistry.get(args.get("-end"), Node.class);
        if (end == null) {
            System.out.println("Error: End node with id " + args.get("-end") + " does not exist.");
            return;
        }

        int lanes = 1;
        if (args.containsKey("-lanes")) {
            try {
                lanes = Integer.parseInt(args.get("-lanes"));
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format for lanes. Using default value of 1.");
            }
        }

        RoadSegment road;
        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("road")) {
                road = new RoadSegment(args.get("-id"), lanes, start, end);
            }
            else if (type.equals("bridge")) {
                road = new Bridge(args.get("-id"), lanes, start, end);
            }
            else if (type.equals("tunnel")) {
                road = new Tunnel(args.get("-id"), lanes, start, end);
            }
            else {
                System.out.println("Error: Invalid road type. Use road, bridge, or tunnel. Using default type of road.");
                road = new RoadSegment(args.get("-id"), lanes, start, end);
            }
        }
        else {
            road = new RoadSegment(args.get("-id"), lanes, start, end);
        }

        net.addRoadSegment(road);

    }

    public void modLane(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modlane command.");
            return;
        }

        Lane lane = ObjectRegistry.get(args.get("-id"), Lane.class);
        if (lane == null) {
            System.out.println("Error: Lane with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-snow")) {
            float snow = Float.parseFloat(args.get("-snow"));
            lane.setSnowHeight(snow);
        }

        if (args.containsKey("-iced")) {
            boolean iced = Boolean.parseBoolean(args.get("-iced"));
            lane.setIced(iced);
        }

        if (args.containsKey("-icing")){
            int icing = Integer.parseInt(args.get("-icing"));
            lane.setIcingProgress(icing);
        }

        if (args.containsKey("-block")) {
            boolean block = Boolean.parseBoolean(args.get("-block"));
            lane.setVehicleBlock(block);
        }

        if (args.containsKey("-debris")) {
            boolean debris = Boolean.parseBoolean(args.get("-debris"));
            lane.setIceDebris(debris);
        }

        if (args.containsKey("-salted")) {
            int salted = Integer.parseInt(args.get("-salted"));
            lane.setSaltedTimer(salted);
        }

        if (args.containsKey("-gravel")) {
            float gravel = Float.parseFloat(args.get("-gravel"));
            lane.setGravelHeight(gravel);
        }
    }

    public void modPlow(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modplow command.");
            return;
        }

        SnowPlow plow = ObjectRegistry.get(args.get("-id"), SnowPlow.class);
        if (plow == null) {
            System.out.println("Error: Snow plow with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-head")) {
            String head = args.get("-head");
            PlowHead plowHead = ObjectRegistry.get(head, PlowHead.class);
            plow.equip(plowHead);
        }
    }

    public void modBus(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modbus command.");
            return;
        }

        Bus bus = ObjectRegistry.get(args.get("-id"), Bus.class);
        if (bus == null) {
            System.out.println("Error: Bus with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-start")) {
            BusStop start = ObjectRegistry.get(args.get("-start"), BusStop.class);
            if (start == null) {
                System.out.println("Error: Start node with id " + args.get("-start") + " does not exist.");
                return;
            }
            bus.setStartStop(start);
        }

        if (args.containsKey("-end")) {
            BusStop end = ObjectRegistry.get(args.get("-end"), BusStop.class);
            if (end == null) {
                System.out.println("Error: End node with id " + args.get("-end") + " does not exist.");
                return;
            }
            bus.setEndStop(end);
        }

        if (args.containsKey("-inactive")) {
            int inactive = Integer.parseInt(args.get("-inactive"));
            bus.setInactiveTimer(inactive);
        }
    }

    public void modCar(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modcar command.");
            return;
        }

        Car car = ObjectRegistry.get(args.get("-id"), Car.class);
        if (car == null) {
            System.out.println("Error: Car with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-apartment")) {
            Apartment apartment = ObjectRegistry.get(args.get("-apartment"), Apartment.class);
            if (apartment == null) {
                System.out.println("Error: Apartment with id " + args.get("-apartment") + " does not exist.");
                return;
            }
            car.setApartment(apartment);
        }

        if (args.containsKey("-workplace")) {
            Workplace workplace = ObjectRegistry.get(args.get("-workplace"), Workplace.class);
            if (workplace == null) {
                System.out.println("Error: Workplace with id " + args.get("-workplace") + " does not exist.");
                return;
            }
            car.setWorkplace(workplace);
        }
    }

    public void listRoots(Map<String, String> args) {
        Logger.logLine("Root objects details:");
        Logger.logLine("Road networks:");
        for (RoadNetwork road : GameLogic.getInstance().getRoads()) {
            Logger.logLine("- " + road.id);
        }

        Logger.logLine("Cars:");
        for (Car car : GameLogic.getInstance().getCars()) {
            Logger.logLine("- " + car.id);
        }

        Logger.logLine("Players:");
        for (Player player : GameLogic.getInstance().getPlayers()) {
            Logger.logLine("- " + player.id);
        }
    }

    public void inspect(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for inspect command.");
            return;
        }

        Inspectable inspectable = ObjectRegistry.get(args.get("-id"), Inspectable.class);
        if (inspectable != null) {
            inspectable.inspect();
        } else {
            System.out.println("Object with id " + args.get("-id") + " is not inspectable or does not exist.");
        }
    }
}
