package utils;
import java.util.HashMap;
import java.util.Map;
import gamelogic.Apartment;
import gamelogic.BioKerosene;
import gamelogic.BlowerHead;
import gamelogic.Bus;
import gamelogic.BusPlayer;
import gamelogic.BusStop;
import gamelogic.Buyable;
import gamelogic.Car;
import gamelogic.DragonHead;
import gamelogic.GameLogic;
import gamelogic.Gravel;
import gamelogic.GravelThrowerHead;
import gamelogic.IceBreakerHead;
import gamelogic.Inspectable;
import gamelogic.Inventory;
import gamelogic.Lane;
import gamelogic.MoneyBank;
import gamelogic.Node;
import gamelogic.Player;
import gamelogic.PlowHead;
import gamelogic.RoadNetwork;
import gamelogic.Salt;
import gamelogic.SalterHead;
import gamelogic.SnowPlow;
import gamelogic.SnowPlowPlayer;
import gamelogic.SweeperHead;
import gamelogic.Vehicle;
import gamelogic.Workplace;

public class CommandInterpreter {
    private final TestRunner testRunner;

    public interface Command {
        void execute(Map<String, String> args, CommandInterpreter interpreter);
        
    }

    Map<String, Command> commands = Map.ofEntries(
        Map.entry("/test", new utils.commands.Test()), 
        Map.entry("/testAll", new utils.commands.TestAll()),
        Map.entry("/removeTestOutputs", new utils.commands.RemoveTestOutput()),
        Map.entry("help", new utils.commands.Help()),
        Map.entry("/help", new utils.commands.Help()),
        Map.entry("/random", new utils.commands.Random()),
        Map.entry("/seed", new utils.commands.Seed()),
        Map.entry("/logging", new utils.commands.Logging()),
        Map.entry("/addNet", new utils.commands.AddNet()),
        Map.entry("/start", new utils.commands.Start()),
        Map.entry("/saveLog", new utils.commands.SaveLog()),
        Map.entry("/saveGame", new utils.commands.SaveGame()),
        Map.entry("/loadGame", new utils.commands.LoadGame()),
        Map.entry("/generate", new utils.commands.Generate()),
        Map.entry("/addNode", new utils.commands.AddNode()),
        Map.entry("/addRoad", new utils.commands.AddRoad()),
        Map.entry("/modLane", (args, cmd) -> modLane(args)),
        Map.entry("/modPlow", (args, cmd) -> modPlow(args)),
        Map.entry("/modBus", (args, cmd) -> modBus(args)),
        Map.entry("/modCar", (args, cmd) -> modCar(args)),
        Map.entry("/modApartment", (args, cmd) -> modApartment(args)),
        Map.entry("/modWorkplace", (args, cmd) -> modWorkplace(args)),
        Map.entry("/addPlayer",  (args, cmd) -> addPlayer(args)),
        Map.entry("/addVehicle", (args, cmd) -> addVehicle(args)),
        Map.entry("/createBuyable", (args, cmd) -> createBuyable(args)),
        Map.entry("/addMoney", (args, cmd) -> addMoney(args)),
        Map.entry("/setMoney", (args, cmd) -> setMoney(args)),
        Map.entry("/clean", (args, cmd) -> clean(args)),
        Map.entry("/movevehicle", (args, cmd) -> moveVehicle(args)),
        Map.entry("/enter", (args, cmd) -> enter(args)),
        Map.entry("/slip", (args, cmd) -> slip(args)),
        Map.entry("listRoots", (args, cmd) -> listRoots(args)),
        Map.entry("/listRoots", (args, cmd) -> listRoots(args)),
        Map.entry("inspect", (args, cmd) -> inspect(args)),
        Map.entry("/inspect", (args, cmd) -> inspect(args)),
        Map.entry("move", (args, cmd) -> move(args)),
        Map.entry("/move", (args, cmd) -> move(args)),
        Map.entry("changeLane", (args, cmd) -> changeLane(args)),
        Map.entry("/changeLane", (args, cmd) -> changeLane(args)),
        Map.entry("equip", (args, cmd) -> equip(args)),
        Map.entry("/equip", (args, cmd) -> equip(args)),
        Map.entry("buy", (args, cmd) -> buy(args)),
        Map.entry("/buy", (args, cmd) -> buy(args))
    );

    public CommandInterpreter() {
        testRunner = new TestRunner();
    }

    public TestRunner getTestRunner() {
        return testRunner;
    }

    public void execute(String command) {
            String[] parts = command.split("\\s+");
            String commandName = parts[0];
            
            Map<String, String> args = new HashMap<>(); 
            for (int i = 1; i + 1 < parts.length; i+=2) {
                String argName = parts[i];
                String argValue = parts[i+1];
                args.put(argName, argValue);
            }

            Command cmd = commands.get(commandName);
            if (cmd != null) {
                cmd.execute(args, this);
                Logger.logCommand(command);
            } else {
                Logger.logError("Unknown command: " + commandName);
                Logger.logError("Run help for list of available commands.");
            }
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

    public void addVehicle(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for addVehicle command.");
            return;
        }
        if (!args.containsKey("-lane")) {
            System.out.println("Error: Missing -lane argument for addVehicle command.");
            return;
        }
        if (!args.containsKey("-type")) {
            System.out.println("Error: Missing -type argument for addVehicle command.");
            return;
        }

        if (!args.containsKey("-net")) {
            System.out.println("Error: Missing -net argument for addVehicle command.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            System.out.println("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        String vehicleType = args.get("-type");
        switch (vehicleType) {
            case "car":
                Car newCar = new Car(args.get("-id"));
                ObjectRegistry.register(args.get("-id"), newCar);
                GameLogic.getInstance().addCar(newCar, ObjectRegistry.get(args.get("-lane"), Lane.class)); 
                break;
            case "snowplow":
                if (!args.containsKey("-player")) {
                    System.out.println("Error: Missing -player argument for adding snowplow. A snowplow must be assigned to a player.");
                    return;
                }
                SnowPlow newPlow = new SnowPlow(args.get("-id"));
                ObjectRegistry.register(args.get("-id"), newPlow);
                SnowPlowPlayer player = ObjectRegistry.get(args.get("-player"), SnowPlowPlayer.class);
                player.addSnowPlow(newPlow, ObjectRegistry.get(args.get("-lane"), Lane.class));

                break;
            default:
                System.out.println("Error: Invalid vehicle type. Use snowplow, bus or car.");
                return;
        }
    }

    public void addPlayer(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-lane")) {
            Logger.logError("Error: Missing -id -net -lane arguments for addplayer command.");
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
                GameLogic.getInstance().addPlayer(new SnowPlowPlayer(args.get("-id"), net, lane));
            }
            else if (type.equals("bus")){
                GameLogic.getInstance().addPlayer(new BusPlayer(args.get("-id"), net, lane));
            }
            else {
                Logger.logError("Error: Invalid player type. Use snowplow or bus. Using default type of snowplow.");
                GameLogic.getInstance().addPlayer(new SnowPlowPlayer(args.get("-id"), net, lane));
            }
        }else{
            Logger.logError("Error: Missing -type argument for addplayer command. Using default type of snowplow.");
        }

    }

    public void createBuyable(Map<String, String> args) {
        if (!args.containsKey("-id") || !args.containsKey("-type")) {
            Logger.logError("Error: Missing -id argument for createbuyable command.");
            return;
        }

        int amount = 1;
        if (args.containsKey("-amount")) {
            try {
                amount = Integer.parseInt(args.get("-amount"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for amount. Using default value of 1.");
            }
        }

        int price = 0;
        if (args.containsKey("-price")) {
            try {
                price = Integer.parseInt(args.get("-price"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for price. Using default value of 0.");
            }
        }

        String type = args.get("-type");
        Buyable buyable;
        switch (type) {
            case "salt" -> buyable = new Salt(args.get("-id"), amount, price);
            case "kerosene" -> buyable = new BioKerosene(args.get("-id"), amount, price);
            case "gravel" -> buyable = new Gravel(args.get("-id"), amount, price);
            case "sweeperhead" -> buyable = new SweeperHead(args.get("-id"), price);
            case "blowerhead" -> buyable = new BlowerHead(args.get("-id"), price);
            case "salterhead" -> buyable = new SalterHead(args.get("-id"), price);
            case "dragonhead" -> buyable = new DragonHead(args.get("-id"), price);
            case "gravelhead" -> buyable = new GravelThrowerHead(args.get("-id"), price);
            case "icebreakerhead" -> buyable = new IceBreakerHead(args.get("-id"), price);
            default -> Logger.logError("Error: Invalid buyable type.");
        }
    }

    public void addMoney(Map<String, String> args) {
        if (!args.containsKey("-bank") || !args.containsKey("-amount")) {
            Logger.logError("Error: Missing arguments for addmoney command. Required: -bank, -amount.");
            return;
        }

        MoneyBank bank = ObjectRegistry.get(args.get("-bank"), MoneyBank.class);
        if (bank == null) {
            Logger.logError("Error: Money bank with id " + args.get("-bank") + " does not exist.");
            return;
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(args.get("-amount"));
        } catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format for amount. Please provide a valid integer.");
            return;
        }

        bank.addMoney(amount);
    }

    public void setMoney(Map<String, String> args) {
        if (!args.containsKey("-bank") || !args.containsKey("-amount")) {
            Logger.logError("Error: Missing arguments for setmoney command. Required: -bank, -amount.");
            return;
        }

        MoneyBank bank = ObjectRegistry.get(args.get("-bank"), MoneyBank.class);
        if (bank == null) {
            Logger.logError("Error: Money bank with id " + args.get("-bank") + " does not exist.");
            return;
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(args.get("-amount"));
        } catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format for amount. Please provide a valid integer.");
            return;
        }

        bank.setMoney(amount);
    }

    public void clean(Map<String, String> args){
        if (!args.containsKey("-head") || !args.containsKey("-target")) {
            Logger.logError("Error: Missing arguments for clean command. Required: -head, -target.");
            return;
        }
        
        PlowHead head = ObjectRegistry.get(args.get("-head"), PlowHead.class);
        if (head == null) {
            Logger.logError("Error: Plow head with id " + args.get("-head") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-target"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-target") + " does not exist.");
            return;
        }

        head.clean(target);
    }

    public void moveVehicle(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-lane") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for movevehicle command. Required: -vehicle, -lane, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-lane"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-lane") + " does not exist.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        vehicle.setLocation(target);
        
    }

    public void enter(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target")) {
            Logger.logError("Error: Missing arguments for enter command. Required: -vehicle, -target.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Node target = ObjectRegistry.get(args.get("-target"), Node.class);
        if (target == null) {
            Logger.logError("Error: Node with id " + args.get("-target") + " does not exist.");
            return;
        }

        target.accept(vehicle);
    }

    public void slip(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for slip command. Required: -vehicle, -target, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-target"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-target") + " does not exist.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        net.slip(vehicle, target);
    }

    public void listRoots(Map<String, String> args) {
        Logger.logLine("Root objects details:");
        Logger.logLine("Road network:");
        if (GameLogic.getInstance().getRoads() != null) {
            Logger.logLine("- " + GameLogic.getInstance().getRoads().id);
        }
        else {
            Logger.logLine("- No road network found.");
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

    public void move(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for move command. Required: -vehicle, -target, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Node target = ObjectRegistry.get(args.get("-target"), Node.class);
        if (target == null) {
            Logger.logError("Error: Node with id " + args.get("-target") + " does not exist.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }
        
        GameLogic.getInstance().moveVehicle(vehicle, target);
    }

    public void changeLane(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-lane") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for changelane command. Required: -vehicle, -lane, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        int laneNumber = 0;
        try {
            laneNumber = Integer.parseInt(args.get("-lane"));
        } catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format for lane. Please provide a valid integer.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        GameLogic.getInstance().changeLane(vehicle, laneNumber);
    }

    public void equip(Map<String, String> args) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-head")) {
            Logger.logError("Error: Missing arguments for equip command. Required: -vehicle, -head.");
            return;
        }

        SnowPlow plow = ObjectRegistry.get(args.get("-vehicle"), SnowPlow.class);
        if (plow == null) {
            Logger.logError("Error: Snow plow with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        PlowHead head = ObjectRegistry.get(args.get("-head"), PlowHead.class);
        if (head == null) {
            Logger.logError("Error: Plow head with id " + args.get("-head") + " does not exist.");
            return;
        }

        plow.equip(head);
    }

    public void buy(Map<String, String> args) {
        if (!args.containsKey("-buyable") || !args.containsKey("-inventory") || !args.containsKey("-bank")) {
            Logger.logError("Error: Missing arguments for buy command. Required: -buyable, -inventory, -bank.");
            return;
        }

        Buyable buyable = ObjectRegistry.get(args.get("-buyable"), Buyable.class);
        if (buyable == null) {
            Logger.logError("Error: Buyable with id " + args.get("-buyable") + " does not exist.");
            return;
        }

        Inventory inventory = ObjectRegistry.get(args.get("-inventory"), Inventory.class);
        if (inventory == null) {
            Logger.logError("Error: Inventory with id " + args.get("-inventory") + " does not exist.");
            return;
        }

        MoneyBank moneyBank = ObjectRegistry.get(args.get("-bank"), MoneyBank.class);
        if (moneyBank == null) {
            Logger.logError("Error: MoneyBank with id " + args.get("-bank") + " does not exist.");
            return;
        }

        buyable.buy(inventory, moneyBank);
    }
}
