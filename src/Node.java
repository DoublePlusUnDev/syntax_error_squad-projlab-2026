/**
 * A node in the road network. When a vehicle arrives on a road segment, 
 * the vehicle will enter the two neighbouring nodes. 
 */
public class Node implements Inspectable{
    String id;

    public Node(String id) {
        this.id = id;
        ObjectRegistry.register(id, this);
    }

    public void accept(Vehicle vehicle) {
        
    }

    @Override
    public String inspect() {
        return "Node " + id;
    }
}
