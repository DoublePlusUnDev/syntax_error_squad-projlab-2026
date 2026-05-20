package gamelogic;

public class SnowPlowBuyable extends Buyable {

    public SnowPlowBuyable(String id, int cost) {
        super(id, cost);
    }

    @Override 
    public boolean buy(Inventory inventory, Player player) {
        if (!super.buy(inventory, player))
            return false;

        //player.addVehicle(new SnowPlow());
        return true;
    }

    @Override
    public void inspect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inspect'");
    }
    
}
