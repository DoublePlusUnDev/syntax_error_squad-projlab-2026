import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    RoadNetwork roads;
    List<Car> cars;
    List<Player> players;
    List<Updatable> updatables;

    static GameLogic instance;

    public GameLogic() {
        roads = new RoadNetwork();
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
}
