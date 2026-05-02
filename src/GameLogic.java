import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    List<RoadNetwork> roads;
    List<Car> cars;
    List<Player> players;
    List<Updatable> updatables;

    static GameLogic instance;

    public GameLogic() {
        roads = new ArrayList<>();
        cars = new ArrayList<>();
        players = new ArrayList<>();
        updatables = new ArrayList<>();
        
    }

    public void registerUpdatable(Updatable updatable) {
        updatables.add(updatable);
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
}
