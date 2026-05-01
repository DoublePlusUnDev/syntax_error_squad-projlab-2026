import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    static Random random = new Random();
    private static RandomGeneratorMode mode = RandomGeneratorMode.RANDOM;
    
    public static void setMode(RandomGeneratorMode newMode) {
        mode = newMode;
    }
    public static void setSeed(String seed){
        random.setSeed(seed.hashCode());
    }
    
    public static int getRandomInt(int min, int max) {
        if (mode == RandomGeneratorMode.ALWAYS || mode == RandomGeneratorMode.NEVER)
            throw new IllegalStateException("RandomGenerator is in " + mode + " mode, cannot generate random numbers.");
        return random.nextInt(max - min + 1) + min;
    }

    public static <T> void shuffleList(List<T> list) {
        Collections.shuffle(list, random);
    }

    boolean decision(double probability) {
        if (mode == RandomGeneratorMode.ALWAYS) {
            return true;
        } else if (mode == RandomGeneratorMode.NEVER) {
            return false;
        } else {
            return random.nextDouble() < probability;
        }
    }
}
