package hangmanproject;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    // logger to record errors and other information
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    // reads all of the lines from a file
    public List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim()); // adds each line to the list after trimming
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: " + filePath, e);
            return Collections.emptyList();  // returns an empty list if an error occurs
        }
        return lines;
    }

    // reads the entire file as a single string
    public String readFileAsString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file as string: " + filePath, e);
            return "";
        }
        return content.toString().trim();
    }

    // reads a specific line by line number
    public Optional<String> readLineByNumber(String filePath, int lineNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                if (currentLine == lineNumber) {
                    return Optional.of(line.trim());
                }
                currentLine++;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading specific line from file: " + filePath, e);
        }
        return Optional.empty();
    }

    // method that writes a list of lines to a file
    public void writeLines(String filePath, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (String line : lines) {
                pw.println(line);  // writes each line to the file
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file: " + filePath, e);
        }
    }

    // method to append a single line to a file
    public void appendLine(String filePath, String line) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pw.println(line);  // writes the line to the file
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error appending to file: " + filePath, e);
        }
    }

    // overwrites specific lines in a file
    public void overwriteLine(String filePath, int lineNumber, String newLine) {
        List<String> lines = readLines(filePath);
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, newLine);
            writeLines(filePath, lines);
        } else {
            LOGGER.log(Level.WARNING, "Line number out of range: {0}", lineNumber);
        }
    }

    // deletes a specific line in a file
    public void deleteLine(String filePath, int lineNumber) {
        List<String> lines = readLines(filePath);
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber);
            writeLines(filePath, lines);
        } else {
            LOGGER.log(Level.WARNING, "Line number out of range: {0}", lineNumber);
        }
    }
}
