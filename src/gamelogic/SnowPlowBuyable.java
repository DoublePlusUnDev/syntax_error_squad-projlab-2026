package gamelogic;

import gamelogic.buyables.Buyable;
import utils.ObjectRegistry;

public class SnowPlowBuyable extends Buyable {

    public SnowPlowBuyable(String id, int cost) {
        super(id, cost);
    }

    @Override 
    public boolean buy(Inventory inventory, Player player) {
        if (!super.buy(inventory, player))
            return false;

        try {
            if (player instanceof SnowPlowPlayer spp) {
                Lane free = spp.roads.getFreeLane();
                SnowPlow newPlow = new SnowPlow(id + ".p", spp);
                ObjectRegistry.register(newPlow.id, newPlow);
                if (free != null) {
                    spp.addSnowPlow(newPlow, free);
                } else {
                    // No free lane: still add to player's list but without placement
                    spp.getSnowPlows().add(newPlow);
                }

                // Create a sweeper head and add it to the new plow's inventory, then equip it
                try {
                    gamelogic.buyables.SweeperHead head = new gamelogic.buyables.SweeperHead(newPlow.id + ".sweeper", 0);
                    // Buy the head into the snowplow's inventory (price 0 so no extra cost)
                    head.buy(newPlow.getInventory(), player);
                    newPlow.equip(head);
                } catch (Exception ex2) {
                    // ignore sweeper creation failures
                }
            }
        } catch (Exception ex) {
            // ignore placement failures
        }

        return true;
    }

    @Override
    public void inspect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inspect'");
    }
    
}
