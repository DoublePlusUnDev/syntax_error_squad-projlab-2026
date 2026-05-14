import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public GamePanel() {
        setLayout(new BorderLayout());

        
        JPanel infoPages = new JPanel();
        CardLayout cardLayout = new CardLayout();
        infoPages.setLayout(cardLayout);
    }
}
