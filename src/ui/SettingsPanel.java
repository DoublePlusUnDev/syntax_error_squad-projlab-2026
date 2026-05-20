package ui;
import gamelogic.GameSettings;
import gamelogic.RoadGenerationParameters;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel{

    private SettingsManager settingsManager;

    private boolean booleanSetter(String key, String newValue) {
        try {
            boolean value = Boolean.parseBoolean(newValue);
            settingsManager.setSetting(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean integerSetter(String key, String newValue) {
        try {
            int value = Integer.parseInt(newValue);
            settingsManager.setSetting(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SettingsPanel(GameWindow gameWindow, SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        contentPanel.setBackground(UIStyles.backgroundColor);
        setBackground(UIStyles.backgroundColor);

        contentPanel.add(new SettingsCardCheckBox("Várókép: ", false, 
            value -> booleanSetter("turnScreen", String.valueOf(value))));
        contentPanel.add(new SettingsCardValueField("Hókotró játékosok: ", (Integer) GameSettings.defaultSettings.getSetting(GameSettings.SNOW_PLOW_PLAYERS_KEY), 
            value -> integerSetter(GameSettings.SNOW_PLOW_PLAYERS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Busz játékosok: ", (Integer) GameSettings.defaultSettings.getSetting(GameSettings.BUS_PLAYERS_KEY), 
            value -> integerSetter(GameSettings.BUS_PLAYERS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Körök száma: ", 50, 
            value -> integerSetter("rounds", value)));
        contentPanel.add(new SettingsCardValueField("Havazás esélye: ", (Integer) GameSettings.defaultSettings.getSetting(GameSettings.SNOW_CHANCE_KEY), 
            value -> integerSetter(GameSettings.SNOW_CHANCE_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Havazás csomópontok: ", (Integer) GameSettings.defaultSettings.getSetting(GameSettings.SNOW_NODES_KEY), 
            value -> integerSetter(GameSettings.SNOW_NODES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Node min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.NODE_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Node max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.NODE_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Main lanes: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.MAIN_LANES_KEY), 
            value -> integerSetter(RoadGenerationParameters.MAIN_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small nodes min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small nodes max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small node lanes: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_LANES_KEY), 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODE_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small node extra roads: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY), 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big nodes min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.BIG_NODES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big nodes max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.BIG_NODES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big node lanes: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_LANES_KEY), 
            value -> integerSetter(RoadGenerationParameters.BIG_NODE_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big node extra roads: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY), 
            value -> integerSetter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bus stops min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.BUS_STOPS_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bus stops max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.BUS_STOPS_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Work places min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.WORK_PLACES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Work places max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.WORK_PLACES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Apartments min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.APARTMENTS_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Apartments max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.APARTMENTS_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bridges min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BRIDGES_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.BRIDGES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bridges max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BRIDGES_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.BRIDGES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Tunnels min: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.TUNNELS_MIN_KEY), 
            value -> integerSetter(RoadGenerationParameters.TUNNELS_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Tunnels max: ", (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.TUNNELS_MAX_KEY), 
            value -> integerSetter(RoadGenerationParameters.TUNNELS_MAX_KEY, value)));

        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = UIFactory.createButton("Vissza", e -> gameWindow.showMainMenu());
        backButton.setFont(backButton.getFont().deriveFont(14.0f));
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 40));
        
        add(backButton, BorderLayout.SOUTH);
    }
}
