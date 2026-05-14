import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GamePanel extends JPanel {

    public GamePanel(CommandInterpreter commandInterpreter) {
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);

        RoadPanel roadPanel = new RoadPanel();
        add(roadPanel, BorderLayout.CENTER);

        JTabbedPane infoPages = new JTabbedPane();
        

        infoPages.addTab("Járművek", new VehiclePanel());
        infoPages.addTab("Készlet", new InventoryPanel());
        //infoPages.addTab("Bolt", new StorePanel());
        infoPages.addTab("Parancsok", new ConsolePanel(commandInterpreter));
        infoPages.setBackground(UIStyles.backgroundColor);
        infoPages.setForeground(UIStyles.textColor);

        infoPages.setPreferredSize(new Dimension(infoPages.getPreferredSize().width, 300));

        add(infoPages, BorderLayout.SOUTH);


    }
}
