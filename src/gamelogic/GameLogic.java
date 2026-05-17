package gamelogic;

import java.util.ArrayList;
import java.util.List;
import utils.Logger;

/**
 * The main class that holds the state of the game and handles the game logic.
 * It manages the players, cars, roads and updatables in the game.
 * It also handles the turn-based system and the interactions between the different entities in the game.
 */
public class GameLogic {
    private final List<Runnable> gameStateChangeListeners = new ArrayList<>();
    private final List<Runnable> topologyChangedListeners = new ArrayList<>();

    private RoadNetwork roads;
    private final List<Car> cars;
    private final List<Player> players;
    private final List<Updatable> updatables;

    private Player currentPlayer = null;
    public static GameLogic instance;

    public GameLogic() {
        roads = null;
        cars = new ArrayList<>();
        players = new ArrayList<>();
        updatables = new ArrayList<>();
        
    }

    public void startGame() {
        if (players.isEmpty()) {
            Logger.logError("NO PLAYERS! FAILING TO START GAME.");
            return;
        }

        currentPlayer = players.get(0);
        startTurn();
    }

    public void startTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN STARTED");
        gameStateChangeListeners.forEach(Runnable::run);
    }

    public void endTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN ENDED");
        int currentIndex = players.indexOf(currentPlayer);
        if (currentPlayer == players.get(players.size() - 1)) {
            Logger.logLine("ROUND ENDED");
            updateAll();
        }
        currentPlayer = players.get((currentIndex + 1) % players.size());
        gameStateChangeListeners.forEach(Runnable::run);
        startTurn();
    }

    public void addGameStateChangeListener(Runnable listener) {
        gameStateChangeListeners.add(listener);
    }

    public void addTopologyChangedListener(Runnable listener) {
        topologyChangedListeners.add(listener);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void moveVehicle(Vehicle vehicle, Node targetNode) {
        roads.tryMoveTowardsNode(vehicle, targetNode);
    }

    public void makeRoads(String id) {
        roads = new RoadNetwork(id);
        roads.addTopologyChangedListener(() -> topologyChangedListeners.forEach(Runnable::run));
    }

    public void changeLane(Vehicle vehicle, int targetLane) {
        roads.changeLane(vehicle, targetLane);
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

    public RoadNetwork getRoads() {
        return roads;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addCar(Car car, Lane lane) {
        cars.add(car);
        car.setLocation(lane);

        if (roads != null && lane != null) {
            roads.placeCar(car);
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setLocation(null);

        if (roads != null) 
            roads.removeCar(car);
    }
}
