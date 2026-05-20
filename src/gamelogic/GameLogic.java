package gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Logger;

/**
 * The main class that holds the state of the game and handles the game logic.
 * It manages the players, cars, roads and updatables in the game.
 * It also handles the turn-based system and the interactions between the different entities in the game.
 */
public class GameLogic {
    private final List<Runnable> gameStateChangeListeners = new ArrayList<>();
    private final List<Runnable> topologyChangedListeners = new ArrayList<>();
    private final List<Runnable> turnEndedListeners = new ArrayList<>();

    private GameSettings gameSettings;

    private RoadNetwork roads;
    private final List<Car> cars;
    private final List<Player> players;
    private final List<Updatable> updatables;

    private Player currentPlayer = null;
    public static GameLogic instance;

    private Map<Vehicle, Boolean> hasMoved;
    private int currentRound = 0;
    

    public GameLogic() {
        roads = null;
        cars = new ArrayList<>();
        players = new ArrayList<>();
        updatables = new ArrayList<>();
        
    }

    public void startGame(GameSettings gameSettings) {
        for (int i = 0; i < (Integer) gameSettings.getSetting(GameSettings.SNOW_PLOW_PLAYERS_KEY); i++) {
            addPlayer(new SnowPlowPlayer("snowplow_player_" + (i + 1), roads, roads.getFreeLane()));
        }
        for (int i = 0; i < (Integer) gameSettings.getSetting(GameSettings.BUS_PLAYERS_KEY); i++) {
            addPlayer(new BusPlayer("bus_player_" + (i + 1), roads, roads.getFreeLane()));
        }

        this.gameSettings = gameSettings;
        if (players.isEmpty()) {
            Logger.logError("NO PLAYERS! FAILING TO START GAME.");
            return;
        }

        currentRound = 1;
        switchPlayer(players.get(0));
        startTurn();
    }

    public void startTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN STARTED");
        gameStateChangeListeners.forEach(Runnable::run);
    }

    public void endTurn() {
        Logger.logLine("PLAYER " + currentPlayer.id + " TURN ENDED");
        int currentPlayerIndex = players.indexOf(currentPlayer);
        if (currentPlayer == players.get(players.size() - 1)) {
            Logger.logLine("ROUND ENDED");
            currentRound++;
            snow();
            updateAll();
        }
        switchPlayer(players.get((currentPlayerIndex + 1) % players.size()));
        gameStateChangeListeners.forEach(Runnable::run);
        turnEndedListeners.forEach(Runnable::run);
        startTurn();
    }

    private void snow() {
        int snowChance = (Integer) gameSettings.getSetting(GameSettings.SNOW_CHANCE_KEY);
        int snowNodes = (Integer) gameSettings.getSetting(GameSettings.SNOW_NODES_KEY);
        
        if (!utils.RandomGenerator.decide(snowChance / 100.0f))
            return;

        roads.snow(snowNodes);
    }

    public void addGameStateChangeListener(Runnable listener) {
        gameStateChangeListeners.add(listener);
    }

    public void addTopologyChangedListener(Runnable listener) {
        topologyChangedListeners.add(listener);
    }

    public void addTurnEndedListener(Runnable listener) {
        turnEndedListeners.add(listener);
    }

    private void switchPlayer(Player player) {
        currentPlayer = player;
        hasMoved = new java.util.HashMap<>();
        if (player instanceof SnowPlowPlayer snowPlowPlayer){
            snowPlowPlayer.getSnowPlows().forEach(snowPlow -> hasMoved.put(snowPlow, false));
        }
        else if (player instanceof BusPlayer busPlayer){
            hasMoved = new java.util.HashMap<>();
            hasMoved.put(busPlayer.getBus(), false);
        }

        gameStateChangeListeners.forEach(Runnable::run);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void moveVehicle(Vehicle vehicle, Node targetNode) {
        if (hasMoved.getOrDefault(vehicle, false)) {
            Logger.logError("Error: Vehicle with id " + vehicle.id + " has already moved this turn.");
            return;
        }
        hasMoved.put(vehicle, true);
        roads.tryMoveTowardsNode(vehicle, targetNode);
    }

    public void makeRoads(String id) {
        roads = new RoadNetwork(id);
        roads.addTopologyChangedListener(() -> topologyChangedListeners.forEach(Runnable::run));
        roads.addStateChangeListener(() -> gameStateChangeListeners.forEach(Runnable::run));
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

    public int getRound() {
        return currentRound;
    }
}
