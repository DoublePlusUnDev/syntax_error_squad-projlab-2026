package gamelogic.buyables;

import gamelogic.Lane;
import utils.Logger;

/**
 * An icebreaker head for a snowplow.
 * Can be given a lane, where it will brake the ice.
 */
public class IceBreakerHead extends PlowHead {

    public IceBreakerHead(String id, int price) {
        super(id, price);
    }

    @Override
    public void clean(Lane lane) {
        float money = lane.breakIce();
        player.getBank().addMoney((int)Math.floor(money));
    }

    @Override
    public void inspect() {
        Logger.logLine("Icebreakerhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
