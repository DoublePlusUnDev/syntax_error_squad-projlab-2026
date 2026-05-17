package utils.commands;

import java.util.Map;

import gamelogic.Bus;
import gamelogic.BusStop;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModBus implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
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
}
