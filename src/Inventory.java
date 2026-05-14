import java.util.ArrayList;
import java.util.List;

/**
 * An inventory for a player to store their money, salt, biokerosene
 * and plowheads in.
 * Said resources may be attempted to be consumed.
 */
public class Inventory implements Inspectable{
    String id;
    private int salt = 0;
    private int gravel = 0;
    private int bioKerosene = 0;
    private List<PlowHead> plowHeads = new ArrayList<>();

    private static final int MAX_SALT = 10;
    private static final int MAX_GRAVEL = 10;
    private static final int MAX_BIO_KEROSENE = 10;

    public Inventory(String id) {
        this.id = id;
        ObjectRegistry.register(id, this);
    }

    /**
     * Attempts to consume one unit of salt from the inventory.
     * @return
     * 
     * If the inventory has at least one unit of salt, it will be consumed and the method will return true.
     */
    public boolean tryConsumeSalt() {    
        boolean canConsume = salt > 0;

        if (!canConsume) 
            return false;
        
        salt--;
        return true;
    }

    /**
     * Attempts to consume one unit of gravel from the inventory.
     * @return
     * 
     * If the inventory has at least one unit of gravel, it will be consumed and the method will return true.
     */
    public boolean tryConsumeGravel() {    
        boolean canConsume = gravel > 0;

        if (!canConsume) 
            return false;
        
        gravel--;
        return true;
    }

    /**
     * Attempts to consume one unit of biokerosene from the inventory.
     * @return
     * 
     * If the inventory has at least one unit of biokerosene, it will be consumed and the method will return true.
     */
    public boolean tryConsumeKerosene() {    
        boolean canConsume = bioKerosene > 0;

        if (!canConsume) 
            return false;
        
        bioKerosene--;
        return true;
    }

    /**
     * Adds the given amount of salt to the inventory, up to the maximum capacity.
     * @param amount
     * 
     * If adding the full amount would exceed the maximum capacity, the inventory will be filled to the maximum and the excess will be discarded.
     */
    public void addSalt(int amount) {
        if (salt + amount > MAX_SALT) {
            salt = MAX_SALT;
        } else {
            salt += amount;
        }
    }

    /**
     * Adds the given amount of gravel to the inventory, up to the maximum capacity.
     * @param amount
     * 
     * If adding the full amount would exceed the maximum capacity, the inventory will be filled to the maximum and the excess will be discarded.
     */
    public void addGravel(int amount) {
        if (gravel + amount > MAX_GRAVEL)
            gravel = MAX_GRAVEL;
        else 
            gravel += amount;
        
    }

    /**
     * Adds the given amount of biokerosene to the inventory, up to the maximum capacity.
     * @param amount
     * 
     * If adding the full amount would exceed the maximum capacity, the inventory will be filled to the maximum and the excess will be discarded.
     */
    public void addKerosene(int amount) {
        if (bioKerosene + amount > MAX_BIO_KEROSENE)
            bioKerosene = MAX_BIO_KEROSENE;
        else 
            bioKerosene += amount;
    }

    /**
     * Adds the given plow head to the inventory.
     * @param plowHead
     * 
     * There is no maximum capacity for plow heads, so the given plow head will always be added to the inventory.
     */
    public void addHead(PlowHead plowHead) {
        plowHeads.add(plowHead);
    }

    public int getSalt() {
        return salt;
    }

    public int getGravel() {
        return gravel;
    }

    public int getKerosene() {
        return bioKerosene;
    }

    public List<PlowHead> getPlowHeads() {
        return plowHeads;
    }

    @Override
    public void inspect() {
        Logger.logLine("Inventory " + id + " details:");
        Logger.logLine("Salt: " + salt + "/" + MAX_SALT);
        Logger.logLine("Gravel: " + gravel + "/" + MAX_GRAVEL);
        Logger.logLine("Bio-Kerosene: " + bioKerosene + "/" + MAX_BIO_KEROSENE);
        Logger.logLine("Plow heads: " + plowHeads.size());
        for (PlowHead plowHead : plowHeads) {
            Logger.logLine("- " + plowHead.id);
        }
    }
}