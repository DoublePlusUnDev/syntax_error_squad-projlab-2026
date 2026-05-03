import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    List<RoadNetwork> roads;
    List<Car> cars;
    List<Player> players;
    List<Updatable> updatables;

    Player currentPlayer;
    static GameLogic instance;

    public GameLogic() {
        roads = new ArrayList<>();
        cars = new ArrayList<>();
        players = new ArrayList<>();
        updatables = new ArrayList<>();
        
    }

    public void start() {
        if (players.isEmpty()) {
            Logger.logError("NO PLAYERS! FAILING TO START GAME.");
            return;
        }

        currentPlayer = players.get(0);
        startTurn();
    }

    public void startTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN STARTED");
    }

    public void endTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN ENDED");
        int currentIndex = players.indexOf(currentPlayer);
        if (currentPlayer == players.get(players.size() - 1)) {
            Logger.logLine("ROUND ENDED");
            updateAll();
        }
        currentPlayer = players.get((currentIndex + 1) % players.size());
        startTurn();
    }

    public void moveVehicle(Vehicle vehicle, RoadNetwork road, Node targetNode) {
        road.tryMoveTowardsNode(vehicle, targetNode);
    }

    public void changeLane(Vehicle vehicle, RoadNetwork road, int targetLane) {
        road.changeLane(vehicle, targetLane);
    }

    public void registerUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    private void updateAll() {
        updatables.forEach(Updatable::update);
    }

    public static GameLogic getInstance(){
        if (instance == null){
            instance = new GameLogic();
        }

        return instance;
    }

    public List<RoadNetwork> getRoads() {
        return roads;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addCar(Car car, Lane lane, RoadNetwork road) {
        cars.add(car);
        car.location = lane;
        road.placeCar(car);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        
    }
}
