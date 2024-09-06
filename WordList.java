package hangmanproject;

import java.util.*;

public class WordList {
    private List<String> words;  // list of words loaded from the file
    private Map<Integer, String> wordMap; // a map to manage words by line number
    private FileManager fileManager;  // file manager to handle file operations
    private String filePath;  // path to the file

    // constructor that loads the word list from a file
    public WordList(String filePath) {
        this.filePath = filePath;
        fileManager = new FileManager();

        // Load words into the List and Map
        words = fileManager.readLines(filePath);
        wordMap = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            wordMap.put(i, words.get(i));
        }

        // checks if the word list is empty or didn't load
        if (words.isEmpty()) {
            throw new IllegalStateException("The word list is empty or could not be loaded. Please check the words.txt file.");
        }
    }

    // method to get a random word from the list
    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));  // selects a random word from the list
    }

    // displays the entire word list as a single string
    public void displayWordListAsString() {
        String wordListContent = fileManager.readFileAsString(filePath);
        System.out.println("Word List Content:\n" + wordListContent);
    }

    // displays a specific word by line number
    public void displaySpecificWord(int lineNumber) {
        Optional<String> word = fileManager.readLineByNumber(filePath, lineNumber);
        word.ifPresentOrElse(
            w -> System.out.println("Word at line " + lineNumber + ": " + w),
            () -> System.out.println("No word found at line " + lineNumber)
        );
    }

    // updates a specific word in the list
    public void updateWordInList(int lineNumber, String newWord) {
        fileManager.overwriteLine(filePath, lineNumber, newWord);
        wordMap.put(lineNumber, newWord);
        System.out.println("Word at line " + lineNumber + " has been updated to: " + newWord);
        words = fileManager.readLines(filePath);
    }

    // removes a specific word from the list
    public void removeWordFromList(int lineNumber) {
        fileManager.deleteLine(filePath, lineNumber);
        wordMap.remove(lineNumber);
        System.out.println("Word at line " + lineNumber + " has been removed.");
        words = fileManager.readLines(filePath);
    }

    // example method to demonstrate usage of HashSet
    public void displayUniqueWords() {
        Set<String> uniqueWords = new HashSet<>(words);
        System.out.println("Unique Words in the List:");
        for (String word : uniqueWords) {
            System.out.println(word);
        }
    }
}
