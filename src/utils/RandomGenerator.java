package utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A generator for creating random numbers and making random decisions.
 * Can operate in different modes to allow for deterministic behavior during testing.
 * In ALWAYS mode, all random decisions will return true and all random numbers will be the minimum value.
 * In NEVER mode, all random decisions will return false and all random numbers will be the maximum value.
 * In RANDOM mode, random numbers and decisions will be generated normally.
 */
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

    public static boolean decide(float probability) {
        if (mode == RandomGeneratorMode.ALWAYS) {
            return true;
        } else if (mode == RandomGeneratorMode.NEVER) {
            return false;
        } else {
            return random.nextDouble() < probability;
        }
    }
}
