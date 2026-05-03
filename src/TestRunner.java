import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TestRunner {
    Path testFolder = Paths.get("resources", "tests");
    List<String> tests = List.of(
            /*"test1", "test2",*/ "test3", "test4", "test5",
            "test6", "test7", "test8", "test9", "test10",
            "test11", "test12", "test13", "test14", "test15",
            "test16", "test17", /*"test18", "test19",*/ "test20",
            "test21", "test22", "test23", "test24", "test25",
            "test26", "test27", "test28");

    public void runAllTests() {
        for (String test : tests) {
            runTest(test);
        }
    }

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
