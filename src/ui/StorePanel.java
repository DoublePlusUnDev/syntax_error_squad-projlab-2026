package ui;

import gamelogic.BioKerosene;
import gamelogic.Buyable;
import gamelogic.GameLogic;
import gamelogic.Gravel;
import gamelogic.Player;
import gamelogic.Salt;
import gamelogic.SnowPlow;
import gamelogic.Vehicle;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class StorePanel extends JPanel {
    VehiclePanel vehiclePanel;
    GameLogic gameLogic;

    private JLabel storeLabel;
    private JLabel moneyLabel;
    private JLabel keroseneBuyable ;
    private JLabel saltBuyable;
    private JLabel gravelBuyable;

    public StorePanel(GameLogic gameLogic, VehiclePanel vehiclePanel) {
        this.vehiclePanel = vehiclePanel;
        this.gameLogic = gameLogic;

        setBackground(UIStyles.backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        storeLabel = UIFactory.createLabel("Bolt", 20);
        add(storeLabel);
        moneyLabel = UIFactory.createLabel("Rendelkezésre álló pénz: ", 16);
        add(moneyLabel);



        keroseneBuyable = UIFactory.createLabel("Kerosene: ", 16);
        keroseneBuyable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                tryBuy(new BioKerosene("Kerosene", 1, 50));
            }
        });
        add(keroseneBuyable);

        saltBuyable = UIFactory.createLabel("Salt: ", 16);
        saltBuyable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {                
                tryBuy(new Salt("Salt", 1, 30));
            }
        });
        add(saltBuyable);

        gravelBuyable = UIFactory.createLabel("Gravel: ", 16);
        gravelBuyable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {                
                tryBuy(new Gravel("Gravel", 1, 20));
            }
        });
        add(gravelBuyable);

        gameLogic.addGameStateChangeListener(this::update);
    }

    public void update() {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        if (currentPlayer != null) {
            moneyLabel.setText("Rendelkezésre álló pénz: " + currentPlayer.getBank().getMoney());
        }
    }
    
    private void tryBuy(Buyable buyable) {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        if (currentPlayer == null)
            return;

        Vehicle selectedVehicle = vehiclePanel.getSelectedVehicle();
        if (selectedVehicle instanceof SnowPlow snowPlow) {
            buyable.buy(snowPlow.getInventory(), currentPlayer);
        }
        
    }
}
