package utils;
import java.util.HashMap;
import java.util.Map;

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
        Map.entry("/modLane", new utils.commands.ModLane()),
        Map.entry("/modPlow", new utils.commands.ModPlow()),
        Map.entry("/modBus", new utils.commands.ModBus()),
        Map.entry("/modCar", new utils.commands.ModCar()),
        Map.entry("/modApartment", new utils.commands.ModApartment()),
        Map.entry("/modWorkplace", new utils.commands.ModWorkplace()),
        Map.entry("/addPlayer",  new utils.commands.AddPlayer()),
        Map.entry("/addVehicle", new utils.commands.AddVehicle()),
        Map.entry("/createBuyable", new utils.commands.CreateBuyable()),
        Map.entry("/addMoney", new utils.commands.AddMoney()),
        Map.entry("/setMoney", new utils.commands.SetMoney()),
        Map.entry("/clean", new utils.commands.Clean()),
        Map.entry("/moveVehicle", new utils.commands.MoveVehicle()),
        Map.entry("/enter", new utils.commands.Enter()),
        Map.entry("/slip", new utils.commands.Slip()),
        Map.entry("listRoots", new utils.commands.ListRoots()),
        Map.entry("/listRoots", new utils.commands.ListRoots()),
        Map.entry("inspect", new utils.commands.Inspect()),
        Map.entry("/inspect", new utils.commands.Inspect()),
        Map.entry("move", new utils.commands.Move()),
        Map.entry("/move", new utils.commands.Move()),
        Map.entry("changeLane", new utils.commands.ChangeLane()),
        Map.entry("/changeLane", new utils.commands.ChangeLane()),
        Map.entry("equip", new utils.commands.Equip()),
        Map.entry("/equip", new utils.commands.Equip()),
        Map.entry("buy", new utils.commands.Buy()),
        Map.entry("/buy", new utils.commands.Buy()),
        Map.entry("endTurn", new utils.commands.EndTurn()),
        Map.entry("/endTurn", new utils.commands.EndTurn())
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
   
}
