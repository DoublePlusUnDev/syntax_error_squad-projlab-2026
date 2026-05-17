package utils.commands;

import gamelogic.Apartment;
import gamelogic.Car;
import gamelogic.Workplace;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModCar implements Command {

    @Override
    public void execute(java.util.Map<String, String> args, CommandInterpreter interpreter) {
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

}
