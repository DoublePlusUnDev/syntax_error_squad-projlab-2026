package ui;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import utils.CommandInterpreter;
import utils.Logger;

public class ConsolePanel extends JPanel {
    private JTextArea output;
    private JTextField input;
    
    private CommandInterpreter commandInterpreter;

    private StringBuilder currentOutput = new StringBuilder();

    public ConsolePanel(CommandInterpreter commandInterpreter) {
        this.commandInterpreter = commandInterpreter;
        
        setBackground(UIStyles.backgroundColor);
        setLayout(new BorderLayout());

        output = new JTextArea();
        output.setEditable(false);
        output.setBackground(UIStyles.backgroundColor);
        output.setForeground(UIStyles.textColor);
        output.setFont(output.getFont().deriveFont(14.0f));
        currentOutput = new StringBuilder();
        for (String line : Logger.logMessages) {
            currentOutput.append(line).append("\n");
        }
        output.setText(currentOutput.toString());

        JScrollPane scrollPane = new JScrollPane(output);
        add(scrollPane, BorderLayout.CENTER);

        input = new JTextField();
        input.setBackground(UIStyles.buttonBackgroundColor);
        input.setForeground(UIStyles.textColor);
        input.setFont(input.getFont().deriveFont(14.0f));
        input.addActionListener(e -> {
            String command = input.getText();
            if (!command.isBlank()) {
                commandInterpreter.execute(command);
                input.setText("");
            }
        });
        

        add(input, BorderLayout.SOUTH);

        Logger.addNewLineListener(this::updated);
    }    

    public void updated(String newLine) {
        currentOutput.append(newLine).append("\n");
        output.setText(currentOutput.toString());
    }
}
