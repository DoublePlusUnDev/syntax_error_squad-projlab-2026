package ui;

import gamelogic.GameLogic;
import gamelogic.Player;
import gamelogic.SnowPlow;
import gamelogic.SnowPlowBuyable;
import gamelogic.SnowPlowPlayer;
import gamelogic.Vehicle;
import gamelogic.buyables.BioKerosene;
import gamelogic.buyables.BlowerHead;
import gamelogic.buyables.Buyable;
import gamelogic.buyables.DragonHead;
import gamelogic.buyables.Gravel;
import gamelogic.buyables.GravelThrowerHead;
import gamelogic.buyables.IceBreakerHead;
import gamelogic.buyables.Salt;
import gamelogic.buyables.SalterHead;
import gamelogic.buyables.SweeperHead;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import utils.CommandInterpreter;

public class StorePanel extends JPanel {
    private final List<Runnable> itemBoughtListeners = new ArrayList<>();

    private final VehiclePanel vehiclePanel;
    private final GameLogic gameLogic;
    private final CommandInterpreter commandInterpreter;

    private final JLabel storeLabel;
    private final JLabel moneyLabel;
    private final JLabel keroseneBuyable ;
    private final JLabel saltBuyable;
    private final JLabel gravelBuyable;
    private final JLabel sweeperHeadBuyable;
    private final JLabel blowerHeadBuyable;
    private final JLabel iceBreakerHeadBuyable;
    private final JLabel salterHeadBuyable;
    private final JLabel gravelThrowerHeadBuyable;
    private final JLabel dragonHeadBuyable;
    private final JLabel snowPlowBuyable;

    private int counter = 0;

    public StorePanel(GameLogic gameLogic, VehiclePanel vehiclePanel, CommandInterpreter commandInterpreter) {
        this.vehiclePanel = vehiclePanel;
        this.gameLogic = gameLogic;
        this.commandInterpreter = commandInterpreter;

        setBackground(UIStyles.backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        storeLabel = UIFactory.createLabel("Bolt", 20);
        add(storeLabel);
        moneyLabel = UIFactory.createLabel("Rendelkezésre álló pénz: ", 16);
        add(moneyLabel);



        keroseneBuyable = createBuyableLabel("Kerosene - ${price} - #{amount}", 30, 10, BioKerosene.class);
        add(keroseneBuyable);

        saltBuyable = createBuyableLabel("Salt: ${price} - #{amount}", 30, 10, Salt.class);
        add(saltBuyable);
        
        gravelBuyable = createBuyableLabel("Gravel: ${price} - #{amount}", 20, 10, Gravel.class);
        add(gravelBuyable);

        sweeperHeadBuyable = createBuyableLabel("Sweeper Head: ${price} - #{amount}", 100, 1, SweeperHead.class);
        add(sweeperHeadBuyable);

        blowerHeadBuyable = createBuyableLabel("Blower Head: ${price} - #{amount}", 100, 1, BlowerHead.class);
        add(blowerHeadBuyable);

        iceBreakerHeadBuyable = createBuyableLabel("Ice Breaker Head: ${price} - #{amount}", 100, 1, IceBreakerHead.class);
        add(iceBreakerHeadBuyable);

        salterHeadBuyable = createBuyableLabel("Salter Head: ${price} - #{amount}", 100, 1, SalterHead.class);
        add(salterHeadBuyable);

        gravelThrowerHeadBuyable = createBuyableLabel("Gravel Thrower Head: ${price} - #{amount}", 100, 1, GravelThrowerHead.class);
        add(gravelThrowerHeadBuyable);

        dragonHeadBuyable = createBuyableLabel("Dragon Head: ${price} - #{amount}", 100, 1, DragonHead.class);
        add(dragonHeadBuyable);

        snowPlowBuyable = createBuyableLabel("Snow Plow: ${price} - #{amount}", 500, 1, SnowPlowBuyable.class);
        add(snowPlowBuyable);

        gameLogic.addGameStateChangeListener(this::update);
        vehiclePanel.addSelectionChangeListener(this::update);
        
    }

    public final JLabel createBuyableLabel(String title, int price, int amount, Class <? extends Buyable> buyableClass) {
        String modfiedTitle = title.replace("{price}", String.valueOf(price)).replace("{amount}", String.valueOf(amount));
        JLabel label = UIFactory.createLabel(modfiedTitle, 16);
        label.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {                    
                String buyableName;
                    if (buyableClass == BioKerosene.class) {
                        buyableName = "kerosene";
                    } else if (buyableClass == Salt.class) {
                        buyableName = "salt";
                    } else if (buyableClass == Gravel.class) {
                        buyableName = "gravel";
                    } else if (buyableClass == SweeperHead.class) {
                        buyableName = "sweeperhead";
                    } else if (buyableClass == BlowerHead.class) {
                        buyableName = "blowerhead";
                    } else if (buyableClass == IceBreakerHead.class) {
                        buyableName = "icebreakerhead";
                    } else if (buyableClass == SalterHead.class) {
                        buyableName = "salterhead";
                    } else if (buyableClass == GravelThrowerHead.class) {
                        buyableName = "gravelhead";
                    } else if (buyableClass == DragonHead.class) {
                        buyableName = "dragonhead";
                    } else if (buyableClass == SnowPlowBuyable.class) {
                        buyableName = "snowplow";
                    } else {
                        throw new IllegalStateException("Unexpected value: " + buyableClass);
                    }

                    Vehicle selectedVehicle = vehiclePanel.getSelectedVehicle();
                    if (selectedVehicle instanceof SnowPlow snowPlow){
                        commandInterpreter.execute("/createBuyable -id " + buyableName + counter + " -amount " + amount + " -price " + price + " -type " + buyableName);
                        commandInterpreter.execute("buy -buyable " + buyableName + counter + " -inventory " + snowPlow.getInventory().id + " -player " + gameLogic.getCurrentPlayer().id);
                        itemBoughtListeners.forEach(Runnable::run);
                        counter++;
                    }
            }
        });
        return label;
    }

    public void update() {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        if (currentPlayer != null) {
            moneyLabel.setText("Rendelkezésre álló pénz: " + currentPlayer.getBank().getMoney());
        }

        if (currentPlayer instanceof SnowPlowPlayer){
            keroseneBuyable.setVisible(true);
            saltBuyable.setVisible(true);
            gravelBuyable.setVisible(true);
            sweeperHeadBuyable.setVisible(true);
            blowerHeadBuyable.setVisible(true);
            iceBreakerHeadBuyable.setVisible(true);
            salterHeadBuyable.setVisible(true);
            gravelThrowerHeadBuyable.setVisible(true);
            dragonHeadBuyable.setVisible(true);
            snowPlowBuyable.setVisible(true);
        }
        else{
            keroseneBuyable.setVisible(false);
            saltBuyable.setVisible(false);
            gravelBuyable.setVisible(false);
            sweeperHeadBuyable.setVisible(false);
            blowerHeadBuyable.setVisible(false);
            iceBreakerHeadBuyable.setVisible(false);
            salterHeadBuyable.setVisible(false);
            gravelThrowerHeadBuyable.setVisible(false);
            dragonHeadBuyable.setVisible(false);
            snowPlowBuyable.setVisible(false);
        }
    }

    public void addItemBoughtListener(Runnable listener) {
        itemBoughtListeners.add(listener);
    }
}
