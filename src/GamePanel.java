import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GamePanel extends JPanel {

    GameLogic gameLogic;
    VehiclePanel vehiclePanel;
    InventoryPanel inventoryPanel;
    ConsolePanel consolePanel;

    public GamePanel(CommandInterpreter commandInterpreter, GameLogic gameLogic) {
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);
        this.gameLogic = gameLogic;

        RoadPanel roadPanel = new RoadPanel(gameLogic);
        add(roadPanel, BorderLayout.CENTER);

        JTabbedPane infoPages = new JTabbedPane();
        

        vehiclePanel = new VehiclePanel(gameLogic);
        infoPages.addTab("Járművek", vehiclePanel);

        inventoryPanel = new InventoryPanel();
        infoPages.addTab("Készlet", inventoryPanel);

        //infoPages.addTab("Bolt", new StorePanel());
        
        consolePanel = new ConsolePanel(commandInterpreter);
        infoPages.addTab("Parancsok", consolePanel);

        infoPages.setBackground(UIStyles.backgroundColor);
        infoPages.setForeground(UIStyles.textColor);

        infoPages.setPreferredSize(new Dimension(infoPages.getPreferredSize().width, 300));

        add(infoPages, BorderLayout.SOUTH);

        //gameLogic.addGameStateChangeListener(this::gameStateChanged);
    }

    /*private void gameStateChanged() {
        vehiclePanel.setPlayer(gameLogic.getCurrentPlayer());
    }*/
}
