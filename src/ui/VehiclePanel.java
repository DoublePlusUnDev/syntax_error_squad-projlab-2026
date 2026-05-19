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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VehiclePanel extends JPanel {
    private List<Runnable> selectionChangeListeners = new ArrayList<>();

    private Player player;
    private JLabel topLabel;
    private List<JLabel> vehicleLabels = new ArrayList<>();
    private GameLogic gameLogic;

    private Vehicle selectedVehicle = null;

    public VehiclePanel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIStyles.backgroundColor);

        topLabel = UIFactory.createLabel("Jármű információk itt jelennek meg.", 18.0f);
        topLabel.setHorizontalAlignment(JLabel.LEFT);
        add(topLabel, BorderLayout.NORTH);

        gameLogic.addGameStateChangeListener(this::update);
    }


    private void update(){
        player = gameLogic.getCurrentPlayer();

        if (player == null) {
            topLabel.setText("Nincs játékos kiválasztva.");
            return;
        } 

        topLabel.setText("Járművek: ");
    
        vehicleLabels.forEach(this::remove);
        vehicleLabels.clear();

        switch (player) {
            case SnowPlowPlayer snowPlowPlayer -> {
                for (SnowPlow snowPlow : snowPlowPlayer.getSnowPlows()) {
                    JLabel label = UIFactory.createLabel("- Hókotró " + snowPlow.id, 16.0f);
                    vehicleLabels.add(label);
                    add(label, BorderLayout.CENTER);
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
                vehicleLabels.add(label);
                add(label, BorderLayout.CENTER);
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
                add(label, BorderLayout.CENTER);
            }
        }
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void addSelectionChangeListener(Runnable listener) {
        selectionChangeListeners.add(listener);
    }
}
