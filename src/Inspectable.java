/**
 * An interface for objects that can be inspected.
 * 
 * On inspection they will log their current state to the logger. This can be used for debugging or for providing information to the player.
 */
public interface Inspectable {
    
    public void inspect();
}
