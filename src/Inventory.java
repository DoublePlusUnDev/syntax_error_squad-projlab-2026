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

    private static final int MAX_SALT = 20;
    private static final int MAX_GRAVEL = 20;
    private static final int MAX_BIO_KEROSENE = 20;

    public Inventory(String id) {
        this.id = id;
        ObjectRegistry.register(id, this);
    }

    public boolean tryConsumeSalt() {
        TestUtil.enterFunction("Inventory:tryConsumeSalt()");
    
        boolean canConsume = salt > 0;

        if (!canConsume) {
            TestUtil.exitFunction("no salt to consume");
            return false;
        }

        TestUtil.exitFunction("salt consumed");
        return true;
    }

    public boolean tryConsumeGravel() {
        TestUtil.enterFunction("Inventory:tryConsumeGravel()");
    
        boolean canConsume = gravel > 0;

        if (!canConsume) {
            TestUtil.exitFunction("no gravel to consume");
            return false;
        }

        TestUtil.exitFunction("gravel consumed");
        return true;
    }

    public boolean tryConsumeKerosene() {
        TestUtil.enterFunction("Inventory:tryConsumeKerosene()");
    
        boolean canConsume = bioKerosene > 0;

        if (!canConsume) {
            TestUtil.exitFunction("no kerosene to consume");
            return false;
        }

        TestUtil.exitFunction("kerosene consumed");
        return true;
    }

    public void addSalt(int amount) {
        TestUtil.enterFunction("Inventory:addSalt(amount)");
        
        if (salt + amount > MAX_SALT) {
            salt = MAX_SALT;
        } else {
            salt += amount;
        }

        TestUtil.exitFunction("salt added");
    }

    public void addGravel(int amount) {
        TestUtil.enterFunction("Inventory:addGravel(amount)");

        if (gravel + amount > MAX_GRAVEL) {
            gravel = MAX_GRAVEL;
        } else {
            gravel += amount;
        }

        TestUtil.exitFunction("gravel added");
    }

    public void addKerosene(int amount) {
        TestUtil.enterFunction("Inventory:addKerosene(amount)");
    
        if (bioKerosene + amount > MAX_BIO_KEROSENE) {
            bioKerosene = MAX_BIO_KEROSENE;
        } else {
            bioKerosene += amount;
        }

        TestUtil.exitFunction("kerosene added");
    }

    

    public void addHead(PlowHead plowHead) {
        TestUtil.enterFunction("Inventory:addHead(plowHead)");
        plowHeads.add(plowHead);
        TestUtil.exitFunction("head added");
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("Inventory " + id + " details:\n");
        output.append("Salt: " + salt + "/" + MAX_SALT + "\n");
        output.append("Gravel: " + gravel + "/" + MAX_GRAVEL + "\n");
        output.append("Bio-Kerosene: " + bioKerosene + "/" + MAX_BIO_KEROSENE + "\n");
        return output.toString();
    }
}