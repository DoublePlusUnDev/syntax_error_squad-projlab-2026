package gamelogic.buyables;

import gamelogic.Lane;
import utils.Logger;

public class GravelThrowerHead extends PlowHead {

    public GravelThrowerHead(String id, int price) {
        super(id, price);
    }

    /**
     * Prevents ice from forming on a given lane by throwing gravel on it.
     * @param lane the lane to clean
     */
    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeGravel())
            return;

        float money = 0;
        money += lane.throwGravel();
        player.getBank().addMoney((int)Math.floor(money));
    }
    
    @Override
    public void inspect() {
        Logger.logLine("Gravel Thrower Head " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
