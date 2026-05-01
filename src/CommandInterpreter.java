import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandInterpreter {
    public interface Command {
        void execute(Map<String, String> args);
        
    }

    Map<String, Command> commands = Map.of(
        "/help", this::help, "/generate", this::generate, "/addnet", this::addNet, "inspect", this::inspect
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

    public void addNet(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for addnet command.");
            return;
        }

        GameLogic.getInstance().roads.add(new RoadNetwork(args.get("-id")));
    }

    public void seed(Map<String, String> args) {
        if (!args.containsKey("-seed")) {
            System.out.println("Error: Missing -seed argument for seed command.");
            return;
        }

        String seed = args.get("-seed");
        RandomGenerator.setSeed(seed);
    }

    public void inspect(Map<String, String> args) {
        if (!args.containsKey("-id")) {
            System.out.println("Error: Missing -id argument for inspect command.");
            return;
        }

        Object obj = ObjectRegistry.get(args.get("-id"));
        if (obj instanceof Inspectable) {
            ((Inspectable) obj).inspect();
        } else {
            System.out.println("Object with id " + args.get("-id") + " is not inspectable or does not exist.");
        }
    }
}
