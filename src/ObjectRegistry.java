import java.util.HashMap;
import java.util.Map;

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
