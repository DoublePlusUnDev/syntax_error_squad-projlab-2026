package ui;

import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.Node;
import gamelogic.Vehicle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import utils.CommandInterpreter;

public class GamePanel extends JPanel {

    private transient CommandInterpreter commandInterpreter;
    private transient GameLogic gameLogic;
    private transient VehiclePanel vehiclePanel;
    private transient InventoryPanel inventoryPanel;
    private transient ConsolePanel consolePanel;

    

    public GamePanel(CommandInterpreter commandInterpreter, GameLogic gameLogic) {
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);
        this.commandInterpreter = commandInterpreter;
        this.gameLogic = gameLogic;
        
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

        RoadPanel roadPanel = new RoadPanel(gameLogic, this, vehiclePanel);
        add(roadPanel, BorderLayout.CENTER);

        

        //gameLogic.addGameStateChangeListener(this::gameStateChanged);
    }

    public void laneClicked(Lane lane) {
        Vehicle selectedVehicle = vehiclePanel.getSelectedVehicle();
        
        if (selectedVehicle == null)
            return;

        //same road only lane change
        if (selectedVehicle.getLocation().getSegment() == lane.getSegment()) {
            commandInterpreter.execute("changeLane -vehicle " + selectedVehicle.id + " -lane " + (lane.getCount() + 1) + " -net net");
        }
    }

    public void nodeClicked(Node node) {
        Vehicle selectedVehicle = vehiclePanel.getSelectedVehicle();

        if (selectedVehicle == null)
            return;

        commandInterpreter.execute("move -vehicle " + selectedVehicle.id + " -target " + node.id + " -net net");
    }

    /*private void gameStateChanged() {
        vehiclePanel.setPlayer(gameLogic.getCurrentPlayer());
    }*/
}
