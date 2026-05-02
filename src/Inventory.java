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

    public boolean tryConsumeSalt() {    
        boolean canConsume = salt > 0;

        if (!canConsume) 
            return false;
        
        salt--;
        return true;
    }

    public boolean tryConsumeGravel() {    
        boolean canConsume = gravel > 0;

        if (!canConsume) 
            return false;
        
        gravel--;
        return true;
    }

    public boolean tryConsumeKerosene() {    
        boolean canConsume = bioKerosene > 0;

        if (!canConsume) 
            return false;
        
        bioKerosene--;
        return true;
    }

    public void addSalt(int amount) {
        if (salt + amount > MAX_SALT) {
            salt = MAX_SALT;
        } else {
            salt += amount;
        }
    }

    public void addGravel(int amount) {
        if (gravel + amount > MAX_GRAVEL)
            gravel = MAX_GRAVEL;
        else 
            gravel += amount;
        
    }

    public void addKerosene(int amount) {
        if (bioKerosene + amount > MAX_BIO_KEROSENE)
            bioKerosene = MAX_BIO_KEROSENE;
        else 
            bioKerosene += amount;
    }

    

    public void addHead(PlowHead plowHead) {
        plowHeads.add(plowHead);
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