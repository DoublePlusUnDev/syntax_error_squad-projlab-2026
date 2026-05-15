package ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamelogic.BlowerHead;
import gamelogic.DragonHead;
import gamelogic.GravelThrowerHead;
import gamelogic.IceBreakerHead;
import gamelogic.Inventory;
import gamelogic.PlowHead;
import gamelogic.SalterHead;
import gamelogic.SweeperHead;

public class InventoryPanel extends JPanel {
    private Inventory inventory;
    private JLabel topLabel;
    private JLabel saltLabel;
    private JLabel keroseneLabel;
    private JLabel gravelLabel;
    private JLabel headLabel;
    private List<JLabel> plowHeads;

    public InventoryPanel() {
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

        update();
    }    

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        update();
    }

    private void update() {
        if (inventory == null)
            return;

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
