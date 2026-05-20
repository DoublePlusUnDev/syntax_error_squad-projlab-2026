package gamelogic.buyables;

import gamelogic.Lane;
import utils.Logger;

/**
 * A salterhead for a snowplow.
 * Can be given a lane, which it will salt.
 * Requies snow in it's connecte inventory.
 */
public class SalterHead extends PlowHead {

    public SalterHead(String id, int price) {
        super(id, price);
    }

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeSalt())
            return;

        float money = lane.salt();
        player.getBank().addMoney((int)Math.floor(money));
    }

    @Override
    public void inspect() {
        Logger.logLine("Salterhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
