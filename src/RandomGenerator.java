import java.util.Random;

public class RandomGenerator {
    static Random random = new Random();

    public static void setSeed(String seed){
        random.setSeed(seed.hashCode());
    }
    
    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
