package hangmanproject;

public class Hints {
    private int availableHints; // number of hints available
    
    // constructor sets available hints based on difficulty level
    public Hints(int difficultyLevel) {
        availableHints = 3 - difficultyLevel;  // Fewer hints at higher difficulty
    }

    // uses a hint to reveal a letter in the word
    public boolean useHint(WordList wordList, StringBuilder displayedWord) {
        if (availableHints > 0) { // check if hints are available
            for (int i = 0; i < wordList.getRandomWord().length(); i++) {
                if (displayedWord.charAt(i) == '_') { // find an unrevealed letter
                    displayedWord.setCharAt(i, wordList.getRandomWord().charAt(i)); // reveal letter
                    availableHints--; // decreases hint count
                    return true;
                }
            }
        }
        return false; // no hints used
    }

    // returns the number of available hints
    public int getAvailableHints() {
        return availableHints;
    }
}

