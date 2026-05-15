package utils;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry for managing objects by their IDs.
 * Objects can be registered with a unique ID and later retrieved by that ID.
 * This is useful for keeping track of game objects and allowing them to be accessed for running commands.
 */
public class ObjectRegistry {
    static Map<String, Object> objects = new HashMap<>();

    public static void register(String id, Object obj) {
        objects.put(id, obj);
    }

    public static<T> T get(String id, Class<T> type) {
        Object obj = objects.get(id);
        if (obj != null && type.isInstance(obj)) {
            return type.cast(obj);
        }
        return null;
    }
}
