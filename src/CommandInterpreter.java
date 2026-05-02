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
        Map.entry("/start", this::start),
        Map.entry("/savelog", this::savelog),
        Map.entry("/savegame", this::savegame),
        Map.entry("/loadgame", this::loadgame),
        Map.entry("/generate", this::generate),
        Map.entry("/addnode", this::addNode),
        Map.entry("/addroad", this::addRoad),
        Map.entry("/modlane", this::modLane),
        Map.entry("/modplow", this::modPlow),
        Map.entry("/modbus", this::modBus),
        Map.entry("/modcar", this::modCar),
        Map.entry("/modapartment", this::modApartment),
        Map.entry("/modworkplace", this::modWorkplace),
        Map.entry("/addplayer", this::addPlayer),
        Map.entry("/addvehicle", this::addVehicle),
        Map.entry("/createbuyable", this::createBuyable),
        Map.entry("/addmoney", this::addMoney),
        Map.entry("/setmoney", this::setMoney),
        Map.entry("/movevehicle", this::moveVehicle),
        Map.entry("/enter", this::enter),
        Map.entry("/slip", this::slip),
      
        Map.entry("listroots", this::listRoots),
        Map.entry("inspect", this::inspect),
        Map.entry("move", this::move),
        Map.entry("changelane", this::changeLane),
        Map.entry("equip", this::equip),
        Map.entry("buy", this::buy)
    );

    public CommandInterpreter() {

    }

    public void execute(String command) {
            String[] parts = command.split(" ");
            String commandName = parts[0];
            
            Map<String, String> args = new HashMap<>(); 
            for (int i = 1; i + 1 < parts.length; i+=2) {
                String argName = parts[i];
                String argValue = parts[i+1];
                args.put(argName, argValue);
            }

            Command cmd = commands.get(commandName);
            if (cmd != null) {
                cmd.execute(args);
            } else {
                Logger.logError("Unknown command: " + commandName);
                Logger.logError("Run help for list of available commands.");
            }
    }
    
    public void help(Map<String, String> args) {
        File helpFile = new File("resources/help.txt");
        
        try (Scanner scanner = new Scanner(helpFile)) {
            while (scanner.hasNextLine()) {
                Logger.logLine(scanner.nextLine());
            }
        } catch (Exception e) {
            Logger.logError("Error reading help file: " + e.getMessage());
        }
    }

    public void random(Map<String, String> args) {
        if (!args.containsKey("-mode")) {
            Logger.logError("Error: Missing -mode argument for random command.");
            return;
        }
        String mode = args.get("-mode");
        switch (mode) {
            case "always" -> RandomGenerator.setMode(RandomGeneratorMode.ALWAYS);
            case "never" -> RandomGenerator.setMode(RandomGeneratorMode.NEVER);
            case "random" -> RandomGenerator.setMode(RandomGeneratorMode.RANDOM);
            default -> Logger.logError("Error: Invalid mode for random command. Use always, never, or random.");
        }
    }

    public void seed(Map<String, String> args) {
        if (!args.containsKey("-seed")) {
            Logger.logError("Error: Missing -seed argument for seed command.");
            return;
        }

        String seed = args.get("-seed");
        RandomGenerator.setSeed(seed);
    }

    public void logging(Map<String, String> args) {
        if (!args.containsKey("-enable")) {
            Logger.logError("Error: Missing -enable argument for logging command.");
            return;
        }

        boolean enable = Boolean.parseBoolean(args.get("-enable"));
        Logger.setEnabled(enable);
    }

    public void addNet(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for addnet command.");
            return;
        }

        GameLogic.getInstance().roads.add(new RoadNetwork(args.get("-id")));
    }

    public void start(Map<String, String> args) {
        GameLogic.getInstance().start();
    }

    public void savelog(Map<String, String> args) {
        if (!args.containsKey("-path")) {
            System.out.println("Error: Missing -path argument for savelog command.");
            return;
        }
        Logger.saveLog(args.get("-path"));
    }

    public void savegame(Map<String, String> args) {
        if (!args.containsKey("-path")) {
            System.out.println("Error: Missing -path argument for savelog command.");
            return;
        }
        // TODO: Implement logic to save game state to a file   
    }

    public void loadgame(Map<String, String> args) {
        if (!args.containsKey("-path")) {
            System.out.println("Error: Missing -path argument for loadgame command.");
            return;
        }
        // TODO: Implement logic to load game state from a file
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
            Logger.logError("Error: Missing arguments for addnode command. Required: -id, -net.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
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
                Logger.logError("Error: Invalid node type. Use busstop, workplace, or apartment. Using default type of busstop.");
                net.addNode(new BusStop(args.get("-id")));
            }
        }
        else {
            net.addNode(new BusStop(args.get("-id")));
        }
    }

        

    public void addRoad(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-start") || !args.containsKey("-end")) {
            Logger.logError("Error: Missing arguments for addroad command. Required: -id, -net, -start, -end.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        Node start = ObjectRegistry.get(args.get("-start"), Node.class);
        if (start == null) {
            Logger.logError("Error: Start node with id " + args.get("-start") + " does not exist.");
            return;
        }

        Node end = ObjectRegistry.get(args.get("-end"), Node.class);
        if (end == null) {
            Logger.logError("Error: End node with id " + args.get("-end") + " does not exist.");
            return;
        }

        int lanes = 1;
        if (args.containsKey("-lanes")) {
            try {
                lanes = Integer.parseInt(args.get("-lanes"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for lanes. Using default value of 1.");
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
                Logger.logError("Error: Invalid road type. Use road, bridge, or tunnel. Using default type of road.");
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
            Logger.logError("Error: Missing -id argument for modlane command.");
            return;
        }

        Lane lane = ObjectRegistry.get(args.get("-id"), Lane.class);
        if (lane == null) {
            Logger.logError("Error: Lane with id " + args.get("-id") + " does not exist.");
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
            Logger.logError("Error: Missing -id argument for modplow command.");
            return;
        }

        SnowPlow plow = ObjectRegistry.get(args.get("-id"), SnowPlow.class);
        if (plow == null) {
            Logger.logError("Error: Snow plow with id " + args.get("-id") + " does not exist.");
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
            Logger.logError("Error: Missing -id argument for modbus command.");
            return;
        }

        Bus bus = ObjectRegistry.get(args.get("-id"), Bus.class);
        if (bus == null) {
            Logger.logError("Error: Bus with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-start")) {
            BusStop start = ObjectRegistry.get(args.get("-start"), BusStop.class);
            if (start == null) {
                Logger.logError("Error: Start node with id " + args.get("-start") + " does not exist.");
                return;
            }
            bus.setStartStop(start);
        }

        if (args.containsKey("-end")) {
            BusStop end = ObjectRegistry.get(args.get("-end"), BusStop.class);
            if (end == null) {
                Logger.logError("Error: End node with id " + args.get("-end") + " does not exist.");
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
            Logger.logError("Error: Missing -id argument for modcar command.");
            return;
        }

        Car car = ObjectRegistry.get(args.get("-id"), Car.class);
        if (car == null) {
            Logger.logError("Error: Car with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-apartment")) {
            Apartment apartment = ObjectRegistry.get(args.get("-apartment"), Apartment.class);
            if (apartment == null) {
                Logger.logError("Error: Apartment with id " + args.get("-apartment") + " does not exist.");
                return;
            }
            car.setApartment(apartment);
        }

        if (args.containsKey("-workplace")) {
            Workplace workplace = ObjectRegistry.get(args.get("-workplace"), Workplace.class);
            if (workplace == null) {
                Logger.logError("Error: Workplace with id " + args.get("-workplace") + " does not exist.");
                return;
            }
            car.setWorkplace(workplace);
        }
    }

    public void modApartment(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modapartment command.");
            return;
        }
        Apartment apartment = ObjectRegistry.get(args.get("-id"), Apartment.class);
        if (apartment == null) {
            System.out.println("Error: Apartment with id " + args.get("-id") + " does not exist.");
            return;
        }
        if (args.containsKey("-spawn")) {
            int spawnTimer = Integer.parseInt(args.get("-spawn"));
            apartment.setSpawnTimer(spawnTimer);
        }
    }
     
    public void modWorkplace(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for modworkplace command.");
            return;
        }
        Workplace workplace = ObjectRegistry.get(args.get("-id"), Workplace.class);
        if (workplace == null) {
            System.out.println("Error: Workplace with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-spawn")) {
            int spawnTimer = Integer.parseInt(args.get("-spawn"));
            workplace.setSpawnTimer(spawnTimer);
        }

    }

    public void addPlayer(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for addplayer command.");
            return;
        }
        if (!args.containsKey("-lane")) {
            System.out.println("Error: Missing -lane argument for addplayer command.");
            return;
        }
        if (!args.containsKey("-type")) {
            System.out.println("Error: Missing -type argument for addplayer command.");
            return;
        }
        String playerType = args.get("-type");
        Lane lane = ObjectRegistry.get(args.get("-lane"), Lane.class);

        switch (playerType) {
            case "bus":
                Player newPlayer = new BusPlayer(args.get("-id"), /* TODO: Roadnetwork*/);
                ObjectRegistry.register(args.get("-id"), newPlayer);
                GameLogic.getInstance().players.add(newPlayer);
                //TODO: A járművét a lane-hez adni.
                break;
            case "snowplow":
                Player newPlayer = new SnowPlowPlayer(args.get("-id"), /* TODO: Roadnetwork*/);
                ObjectRegistry.register(args.get("-id"), newPlayer);
                GameLogic.getInstance().players.add(newPlayer);
                //TODO: A járművét a lane-hez adni. 
                break;
            default:
                System.out.println("Error: Invalid player type. Use bus or snowplow.");
                return;
        }
    }

    public void addVehicle(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for addplayer command.");
            return;
        }
        if (!args.containsKey("-lane")) {
            System.out.println("Error: Missing -lane argument for addplayer command.");
            return;
        }
        if (!args.containsKey("-type")) {
            System.out.println("Error: Missing -type argument for addplayer command.");
            return;
        }

        String vehicleType = args.get("-type");
        switch (vehicleType) {
            case "car":
                Car newCar = new Car(args.get("-id"));
                ObjectRegistry.register(args.get("-id"), newCar);
                GameLogic.getInstance().cars.add(newCar);
                //TODO: A járművét a lane-hez adni. 
                break;
            case "snowplow":
                if !args.containsKey("-player") {
                    System.out.println("Error: Missing -player argument for adding snowplow. A snowplow must be assigned to a player.");
                    return;
                }
                SnowPlow newPlow = new SnowPlow(args.get("-id"));
                ObjectRegistry.register(args.get("-id"), newPlow);
                SnowPlowPlayer player = ObjectRegistry.get(args.get("-player"), SnowPlowPlayer.class);
                player.addSnowPlow(newPlow);
                //TODO: A járművét a lane-hez adni. 
                break;
            default:
                System.out.println("Error: Invalid vehicle type. Use snowplow, bus or car.");
                return;
        }
    }

    public void addPlayer(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing -id argument for addplayer command.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        Lane lane = ObjectRegistry.get(args.get("-lane"), Lane.class);
        if (lane == null) {
            Logger.logError("Error: Lane with id " + args.get("-lane") + " does not exist.");
            return;
        }

        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("snowplow")) {
                SnowPlowPlayer player = new SnowPlowPlayer(args.get("-id"), net, lane);
            }
            else if (type.equals("bus")){
                BusPlayer player = new BusPlayer(args.get("-id"), net, lane);
            }
            else {
                Logger.logError("Error: Invalid player type. Use snowplow or bus. Using default type of snowplow.");
                SnowPlowPlayer player = new SnowPlowPlayer(args.get("-id"), net, lane);     
            }
        }else{
            Logger.logError("Error: Missing -type argument for addplayer command. Using default type of snowplow.");
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
            Logger.logError("Error: Missing -id argument for inspect command.");
            return;
        }

        Inspectable inspectable = ObjectRegistry.get(args.get("-id"), Inspectable.class);
        if (inspectable != null) {
            inspectable.inspect();
        } else {
            Logger.logError("Object with id " + args.get("-id") + " is not inspectable or does not exist.");
        }
    }
}
