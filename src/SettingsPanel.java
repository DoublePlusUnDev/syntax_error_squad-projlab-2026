import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel{

    private static boolean booleanSetter(String key, String newValue) {
        try {
            boolean value = Boolean.parseBoolean(newValue);
            Game.setSetting(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean integerSetter(String key, String newValue) {
        try {
            int value = Integer.parseInt(newValue);
            Game.setSetting(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SettingsPanel(GameUI gameUI) {
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        contentPanel.setBackground(UIStyles.backgroundColor);
        setBackground(UIStyles.backgroundColor);

        contentPanel.add(new SettingsCardCheckBox("Várókép: ", false, 
            value -> booleanSetter("turnScreen", String.valueOf(value))));
        contentPanel.add(new SettingsCardValueField("Hókotró játékosok: ", 2, 
            value -> integerSetter("snowPlowPlayers", value)));
        contentPanel.add(new SettingsCardValueField("Busz játékosok: ", 1, 
            value -> integerSetter("busPlayers", value)));
        contentPanel.add(new SettingsCardValueField("Körök száma: ", 50, 
            value -> integerSetter("rounds", value)));
        contentPanel.add(new SettingsCardValueField("Node min: ", 10, 
            value -> integerSetter("nodeMin", value)));
        contentPanel.add(new SettingsCardValueField("Node max: ", 20, 
            value -> integerSetter("nodeMax", value)));
        contentPanel.add(new SettingsCardValueField("Main lanes: ", 2, 
            value -> integerSetter("mainLanes", value)));
        contentPanel.add(new SettingsCardValueField("Small nodes min: ", 2, 
            value -> integerSetter("smallNodesMin", value)));
        contentPanel.add(new SettingsCardValueField("Small nodes max: ", 5, 
            value -> integerSetter("smallNodesMax", value)));
        contentPanel.add(new SettingsCardValueField("Small node lanes: ", 1, 
            value -> integerSetter("smallNodeLanes", value)));
        contentPanel.add(new SettingsCardValueField("Small node extra roads: ", 1, 
            value -> integerSetter("smallNodeExtraRoads", value)));
        contentPanel.add(new SettingsCardValueField("Big nodes min: ", 1, 
            value -> integerSetter("bigNodesMin", value)));
        contentPanel.add(new SettingsCardValueField("Big nodes max: ", 3, 
            value -> integerSetter("bigNodesMax", value)));
        contentPanel.add(new SettingsCardValueField("Big node lanes: ", 3, 
            value -> integerSetter("bigNodeLanes", value)));
        contentPanel.add(new SettingsCardValueField("Big node extra roads: ", 2, 
            value -> integerSetter("bigNodeExtraRoads", value)));
        contentPanel.add(new SettingsCardValueField("Bus stops min: ", 2, 
            value -> integerSetter("busStopsMin", value)));
        contentPanel.add(new SettingsCardValueField("Bus stops max: ", 5, 
            value -> integerSetter("busStopsMax", value)));
        contentPanel.add(new SettingsCardValueField("Work places min: ", 2, 
            value -> integerSetter("workPlacesMin", value)));
        contentPanel.add(new SettingsCardValueField("Work places max: ", 5, 
            value -> integerSetter("workPlacesMax", value)));
        contentPanel.add(new SettingsCardValueField("Apartments min: ", 2, 
            value -> integerSetter("apartmentsMin", value)));
        contentPanel.add(new SettingsCardValueField("Apartments max: ", 5, 
            value -> integerSetter("apartmentsMax", value)));

        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = UIFactory.createButton("Vissza", e -> gameUI.showMainMenu());
        backButton.setFont(backButton.getFont().deriveFont(14.0f));
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 40));
        
        add(backButton, BorderLayout.SOUTH);
    }
}
