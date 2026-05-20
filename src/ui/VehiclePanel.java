package ui;
import gamelogic.Bus;
import gamelogic.BusPlayer;
import gamelogic.GameLogic;
import gamelogic.Player;
import gamelogic.SnowPlow;
import gamelogic.SnowPlowPlayer;
import gamelogic.Vehicle;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.CommandInterpreter;

public class VehiclePanel extends JPanel {
    private List<Runnable> selectionChangeListeners = new ArrayList<>();

    private JLabel topLabel;
    private List<JLabel> vehicleLabels = new ArrayList<>();
    private JButton nextTurnButton;    
    private JPanel vehicleListPanel;

    private GameLogic gameLogic;
    private CommandInterpreter commandInterpreter;
    private Player player;

    private Vehicle selectedVehicle = null;

    public VehiclePanel(GameLogic gameLogic, CommandInterpreter commandInterpreter) {
        this.gameLogic = gameLogic;
        this.commandInterpreter = commandInterpreter;
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);

        vehicleListPanel = new JPanel();
        vehicleListPanel.setLayout(new BoxLayout(vehicleListPanel, BoxLayout.Y_AXIS));
        vehicleListPanel.setBackground(UIStyles.backgroundColor);
        add(vehicleListPanel, BorderLayout.NORTH);

        topLabel = UIFactory.createLabel("Jármű információk itt jelennek meg.", 18.0f);
        topLabel.setHorizontalAlignment(JLabel.LEFT);
        vehicleListPanel.add(topLabel);

        nextTurnButton = UIFactory.createButton("Következő kör", e -> commandInterpreter.execute("endTurn"));
        add(nextTurnButton, BorderLayout.SOUTH);

        gameLogic.addGameStateChangeListener(this::update);
        gameLogic.addRoundEndedListener(() -> {
            selectedVehicle = null;
            selectionChangeListeners.forEach(Runnable::run);
            update();
        });
        addSelectionChangeListener(this::update);
    }


    private void update(){
        player = gameLogic.getCurrentPlayer();

        if (player == null) {
            topLabel.setText("Nincs játékos kiválasztva.");
            return;
        } 

        topLabel.setText(player.id + " járművei: " + gameLogic.getRound() + ". kör");
    
        vehicleLabels.forEach(vehicleListPanel::remove);
        vehicleLabels.clear();

        switch (player) {
            case SnowPlowPlayer snowPlowPlayer -> {
                for (SnowPlow snowPlow : snowPlowPlayer.getSnowPlows()) {
                    JLabel label = UIFactory.createLabel("- Hókotró " + snowPlow.id, 16.0f);
                    
                    if (snowPlow == selectedVehicle)
                        label.setForeground(UIStyles.selectedColor);
                    
                    vehicleLabels.add(label);
                    vehicleListPanel.add(label);
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            selectedVehicle = snowPlow;
                            topLabel.setText("Kiválasztott jármű: Hókotró " + snowPlow.id);
                            selectionChangeListeners.forEach(Runnable::run);     
                        }
                    });
                }
            }
            case BusPlayer busPlayer -> {
                Bus bus = busPlayer.getBus();
                JLabel label = UIFactory.createLabel("- Busz " + bus.id, 16.0f);

                if (bus == selectedVehicle)
                        label.setForeground(UIStyles.selectedColor);

                vehicleLabels.add(label);
                vehicleListPanel.add(label);
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        selectedVehicle = bus;
                        topLabel.setText("Kiválasztott jármű: Busz " + bus.id);
                        selectionChangeListeners.forEach(Runnable::run);
                    }
                });
            }
            default -> {
                JLabel label = UIFactory.createLabel("- Nincs játékos kiválasztva.", 16.0f);
                vehicleLabels.add(label);
                vehicleListPanel.add(label);
            }
        }
        vehicleListPanel.revalidate();
        vehicleListPanel.repaint();
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public final void addSelectionChangeListener(Runnable listener) {
        selectionChangeListeners.add(listener);
    } 
}
