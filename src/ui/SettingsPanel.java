package ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import gamelogic.RoadGenerationParameters;

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

    public SettingsPanel(GameUI gameUI, SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
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
        contentPanel.add(new SettingsCardValueField("Node min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MIN_KEY) : 10, 
            value -> integerSetter(RoadGenerationParameters.NODE_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Node max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.NODE_MAX_KEY) : 20, 
            value -> integerSetter(RoadGenerationParameters.NODE_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Main lanes: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.MAIN_LANES_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.MAIN_LANES_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.MAIN_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small nodes min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small nodes max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY) : 5, 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small node lanes: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_LANES_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_LANES_KEY) : 1, 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODE_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Small node extra roads: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY) : 1, 
            value -> integerSetter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big nodes min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY) : 1, 
            value -> integerSetter(RoadGenerationParameters.BIG_NODES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big nodes max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY) : 3, 
            value -> integerSetter(RoadGenerationParameters.BIG_NODES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big node lanes: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_LANES_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_LANES_KEY) : 3, 
            value -> integerSetter(RoadGenerationParameters.BIG_NODE_LANES_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Big node extra roads: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bus stops min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.BUS_STOPS_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Bus stops max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY) : 5, 
            value -> integerSetter(RoadGenerationParameters.BUS_STOPS_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Work places min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.WORK_PLACES_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Work places max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY) : 5, 
            value -> integerSetter(RoadGenerationParameters.WORK_PLACES_MAX_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Apartments min: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY) : 2, 
            value -> integerSetter(RoadGenerationParameters.APARTMENTS_MIN_KEY, value)));
        contentPanel.add(new SettingsCardValueField("Apartments max: ", RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY) instanceof Integer ? (Integer) RoadGenerationParameters.defaultParams.getParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY) : 5, 
            value -> integerSetter(RoadGenerationParameters.APARTMENTS_MAX_KEY, value)));

        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = UIFactory.createButton("Vissza", e -> gameUI.showMainMenu());
        backButton.setFont(backButton.getFont().deriveFont(14.0f));
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 40));
        
        add(backButton, BorderLayout.SOUTH);
    }
}
