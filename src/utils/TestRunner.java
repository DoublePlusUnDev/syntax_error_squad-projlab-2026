package utils;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRunner {
    Path testFolder = Paths.get("resources", "tests");
    List<String> tests = List.of(
            "test1", "test2", "test3", "test4", "test5",
            "test6", "test7", "test8", "test9", "test10",
            "test11", "test12", "test13", "test14", "test15",
            "test16", "test17", "test18", "test19", "test20",
            "test21", "test22", "test23", "test24", "test25",
            "test26", "test27", "test28");

    public void runAllTests(boolean output) {
        int passedCount = 0;
        for (String test : tests) {
            if (runTest(test, output)) {
                passedCount++;
            }
        }
        Logger.logLine("Tests passed: " + passedCount + "/" + tests.size());
    }

    public boolean runTest(String testName, boolean output) {
        Path inputFile = Paths.get(testFolder.toString(), testName, "input.txt");
        Path outputFile = Paths.get(testFolder.toString(), testName, "output.txt");
        Path expectedFile = Paths.get(testFolder.toString(), testName, "expected.txt");
        Logger.logLine("Running test: " + inputFile.toString());
        Logger.setOutputEnabled(output);
        CommandInterpreter ci = new CommandInterpreter();
        boolean testPassed = false;
        try (Scanner scanner = new Scanner(inputFile.toFile())) {
            Logger.clearLogs();
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                ci.execute(command);
            }
            Logger.saveLog(outputFile);

            if (expectedFile.toFile().exists()) {
                try (Scanner expectedScanner = new Scanner(expectedFile.toFile())) {
                    List<String> expectedLines = readNormalizedLines(expectedScanner);
                    List<String> actualLines = readNormalizedLines(Logger.logMessages);
                    if (expectedLines.equals(actualLines)) {
                        Logger.setOutputEnabled(true);
                        Logger.logLine("Test " + testName + " passed.");
                        Logger.setOutputEnabled(output);
                        testPassed = true;
                    } else {
                        Logger.setOutputEnabled(true);
                        Logger.logLine("Test " + testName + " failed. Output does not match expected.");
                        Logger.setOutputEnabled(output);
                    }
                } catch (FileNotFoundException e) {
                    Logger.logError("Expected output file not found for test: " + testName);
                }
            } else {
                Logger.logLine("No expected output file found for test: " + testName);
            }

        } catch (FileNotFoundException e) {
            Logger.logError("Test file not found: " + testName);
        }
        finally {
            Logger.setOutputEnabled(true);
        }
        return testPassed;
    }

    private List<String> readNormalizedLines(Scanner scanner) {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = normalizeLine(scanner.nextLine());
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    private List<String> readNormalizedLines(List<String> rawLines) {
        List<String> lines = new ArrayList<>();
        for (String rawLine : rawLines) {
            String line = normalizeLine(rawLine);
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    private String normalizeLine(String line) {
        return line.trim().replaceAll("\\s+", " ");
    }

    public void removeTestOutputs() {
        for (String test : tests) {
            Path outputFile = Paths.get(testFolder.toString(), test, "output.txt");
            if (outputFile.toFile().exists()) {
                outputFile.toFile().delete();
            }
        }
    }
}
