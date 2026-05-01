import java.util.HashMap;
import java.util.Map;

public class ObjectRegistry {
    static Map<String, Object> objects = new HashMap<>();
    
    public static Object get(String id) {
        return objects.get(id);
    }

    public static void register(String id, Object obj) {
        objects.put(id, obj);
    }
}
