package hangmanproject;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private static final int MAX_ATTEMPTS = 6;  // maximum number of attempts allowed
    private int attemptsLeft;  // attempts left
    private Set<Character> guessedLetters;  // the set of letters the player has guessed

    // constructor that initializes attempts and guessed letters
    public Player() {
        attemptsLeft = MAX_ATTEMPTS;  // sets attempts to max value
        guessedLetters = new HashSet<>();  // initializes set for guessed letters
    }

    // gets the number of attempts left
    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    // decreases the number of attempts left by one
    public void decreaseAttempts() {
        attemptsLeft--;
    }

    // checks if the player has already guessed a specific letter
    public boolean hasGuessed(char letter) {
        return guessedLetters.contains(letter);
    }

    // adds a letter to the set of guessed letters
    public void addGuessedLetter(char letter) {
        guessedLetters.add(letter);
    }
}
