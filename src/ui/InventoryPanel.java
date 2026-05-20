package ui;

import gamelogic.GameLogic;
import gamelogic.Inventory;
import gamelogic.SnowPlow;
import gamelogic.buyables.BlowerHead;
import gamelogic.buyables.DragonHead;
import gamelogic.buyables.GravelThrowerHead;
import gamelogic.buyables.IceBreakerHead;
import gamelogic.buyables.PlowHead;
import gamelogic.buyables.SalterHead;
import gamelogic.buyables.SweeperHead;
import utils.CommandInterpreter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel {
    private final GameLogic gameLogic;
    private final VehiclePanel vehiclePanel;
    private final StorePanel storePanel;
    private final CommandInterpreter commandInterpreter;
    private Inventory inventory;
    private JLabel topLabel;
    private JLabel saltLabel;
    private JLabel keroseneLabel;
    private JLabel gravelLabel;
    private JLabel headLabel;
    private List<JLabel> plowHeads;

    public InventoryPanel(GameLogic gameLogic, VehiclePanel vehiclePanel, StorePanel storePanel, CommandInterpreter commandInterpreter) {
        this.gameLogic = gameLogic;
        this.vehiclePanel = vehiclePanel;
        this.storePanel = storePanel;
        this.commandInterpreter = commandInterpreter;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIStyles.backgroundColor);
        setPreferredSize(new Dimension(getPreferredSize().width, 360));

        topLabel = UIFactory.createLabel("Tárgyak:", 24f);
        topLabel.setHorizontalAlignment(JLabel.LEFT);
        add(topLabel, BorderLayout.CENTER);

        saltLabel = UIFactory.createLabel("Só: 0", 18f);
        saltLabel.setHorizontalAlignment(JLabel.LEFT);
        add(saltLabel, BorderLayout.CENTER);

        keroseneLabel = UIFactory.createLabel("Kerozin: 0", 18f);
        keroseneLabel.setHorizontalAlignment(JLabel.LEFT);
        add(keroseneLabel, BorderLayout.CENTER);

        gravelLabel = UIFactory.createLabel("Kavics: 0", 18f);
        gravelLabel.setHorizontalAlignment(JLabel.LEFT);
        add(gravelLabel, BorderLayout.CENTER);

        headLabel = UIFactory.createLabel("Kotrófejek: (0)", 18f);
        headLabel.setHorizontalAlignment(JLabel.LEFT);
        add(headLabel, BorderLayout.CENTER);

        plowHeads = new ArrayList<JLabel>();

        vehiclePanel.addSelectionChangeListener(this::update);
        storePanel.addItemBoughtListener(this::update);
    }    

    private void update() {
        SnowPlow selectedPlow = vehiclePanel.getSelectedVehicle() instanceof SnowPlow snowPlow ? snowPlow : null;

        if (selectedPlow == null) {
            return;
        } 

        inventory = selectedPlow.getInventory();

        for (JLabel head : plowHeads) {
            remove(head);
        }
        plowHeads.clear();

        saltLabel.setText("Só: " + inventory.getSalt());
        keroseneLabel.setText("Kerozin: " + inventory.getKerosene());
        gravelLabel.setText("Kavics: " + inventory.getGravel());
        headLabel.setText("Kotrófejek: (" + inventory.getPlowHeads().size() + ")");

        for (PlowHead head : inventory.getPlowHeads()) {
            JLabel headLabel = UIFactory.createLabel("- " + plowHeadName(head), 16f);
            headLabel.setHorizontalAlignment(JLabel.LEFT);
            add(headLabel, BorderLayout.CENTER);
            headLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    commandInterpreter.execute("equip -vehicle " + selectedPlow.id + " -head " + head.id);
                    update();
                    
                }
            });

            plowHeads.add(headLabel);
        }
    }
    
    private String plowHeadName(PlowHead head) {
        if (head instanceof SweeperHead) {
            return "Seprő";
        } else if (head instanceof BlowerHead) {
            return "Fúvó";
        } else if (head instanceof SalterHead) {
            return "Sózó";
        } else if (head instanceof GravelThrowerHead) {
            return "Kavicsvető";
        } else if (head instanceof IceBreakerHead) {
            return "Jégtörő";
        } else if (head instanceof DragonHead) {
            return "Sárkány";
        } else {
            return "Ismeretlen fej";
        }
    }
}
