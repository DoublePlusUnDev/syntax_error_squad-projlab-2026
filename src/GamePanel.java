import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GamePanel extends JPanel {

    public GamePanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);
        
        JTabbedPane infoPages = new JTabbedPane();
        

        infoPages.addTab("Járművek", new VehiclePanel());
        infoPages.addTab("Készlet", new InventoryPanel());
        //infoPages.addTab("Bolt", new StorePanel());

        add(infoPages, BorderLayout.SOUTH);


    }
}
