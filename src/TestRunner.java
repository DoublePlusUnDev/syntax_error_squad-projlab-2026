import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TestRunner {
    Path testFolder = Paths.get("resources", "tests");
    List<String> tests = List.of(
        "test17");

    public void runTest(String testName) {
        Path inputFile = Paths.get(testFolder.toString(), testName, "input.txt");
        Path outputFile = Paths.get(testFolder.toString(), testName, "output.txt");
        Path expectedFile = Paths.get(testFolder.toString(), testName, "expected.txt");
        System.out.println("Running test: " + inputFile.toString());
        CommandInterpreter ci = new CommandInterpreter();
        try (Scanner scanner = new Scanner(inputFile.toFile())) {
            Logger.clearLogs();
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                ci.execute(command);
            }
            Logger.saveLog(outputFile);
        } catch (FileNotFoundException e) {
            Logger.logError("Test file not found: " + testName);
        }
    }
}
