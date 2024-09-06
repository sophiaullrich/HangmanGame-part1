package hangmanproject;

import java.util.Scanner;

public class HangmanGame extends Game {
    private final WordList wordList;
    private Player player;
    private String currentWord;
    private StringBuilder displayedWord;
    private Scanner scanner;
    private HangmanDrawing hangmanDrawing;
    private Hints hints;
    private Name userName;

    // constructor that initializes the word list, scanner, and userName
    public HangmanGame(Scanner scanner) {  
        wordList = new WordList("src/words.txt");
        this.scanner = scanner;
        hangmanDrawing = new HangmanDrawing();
        hints = new Hints(1); 
        userName = new Name(scanner); 

        System.out.println("Welcome to Sophia's Hangman Game, " + userName.getName() + "!");
        askToStartGame(); // ask the user if they want to start the game after entering their name
    }

    // prompts the user if they want to start the game
    private void askToStartGame() {
        System.out.println("Would you like to start the game? (y/n)");

        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("y")) {
            start(new Statistics()); // start the game if the user responds with 'y'
        } else {
            System.out.println("Goodbye, " + userName.getName() + "!");
            System.exit(0); // exit the program if the user doesn't want to start the game
        }
    }

    // resets the game state for a new round
    private void resetGame() {
        currentWord = wordList.getRandomWord();  // Get a new word
        displayedWord = new StringBuilder("_".repeat(currentWord.length()));  // Display underscores for each letter
        player = new Player();  // initializes player
        gameOver = false;  // changes game over status
        hangmanDrawing = new HangmanDrawing();  // resets drawing
    }

    // starts the game loop
    @Override
    public void start(Statistics statistics) {
        boolean playAgain;

        do {
            resetGame();

            // loops until the game is over
            while (!gameOver) {
                System.out.println("Current word: " + getDisplayedWord());
                System.out.println("Attempts left: " + player.getAttemptsLeft());
                System.out.print("Enter a letter, type 'hint' to use a hint, 'exit' to quit, or 'options' for word list options: ");  // Update prompt to include options

                String input = scanner.nextLine().trim();

                // checks if the player wants to exit the game
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Thanks for playing! Exiting the game...");
                    scanner.close();  // Close the scanner
                    System.exit(0);  // End the game and exit the program immediately
                }

                // handle word list options
                if (input.equalsIgnoreCase("options")) {
                    handleWordListOptions();
                    continue;
                }

                if (input.equalsIgnoreCase("hint")) {
                    if (!hints.useHint(wordList, displayedWord)) {
                        System.out.println("No hints available!");
                    }
                    continue;
                }

                if (input.isEmpty()) {
                    System.out.println("Please enter a letter");
                    continue;
                } else if (input.length() > 1) {
                    System.out.println("Only single letter guesses are allowed");
                    continue;
                }

                char guess = input.charAt(0);
                if (!Character.isLetter(guess)) {
                    System.out.println("Enter a valid letter");
                    continue;
                }

                if (player.hasGuessed(guess)) {
                    System.out.println("You already guessed that letter");
                    continue;
                }

                player.addGuessedLetter(guess);  // adds guess to player's guessed letters
                handleGuess(guess);  // checks if the guess is correct

                // checks if the game is over
                if (gameOver) {
                    if (player.getAttemptsLeft() == 0) {
                        System.out.println("Game over. The word was: " + currentWord + ". Better luck next time, " + userName.getName() + "!");
                        statistics.recordGame(false);  // record game as lost
                    } else {
                        System.out.println("Congratulations, " + userName.getName() + "! You guessed the word: " + currentWord);
                        statistics.recordGame(true);  // record game as won
                    }

                    break;  // Exit the game loop after game over
                }
            }

            // asks if the player wants to play again after the game ends
            System.out.print("Play again? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("y");  // asks if the player wants to play again

        } while (playAgain);  // Loop only if the player wants to play again

        // exits the program after playing once or deciding not to play again
        System.out.println("Thanks for playing Hangman, " + userName.getName() + "!");
        statistics.displayStats();  // display statistics at the end
        System.exit(0); // End the program here to avoid any further code execution
    }

    // handles the player's guess and updates the game state
    private void handleGuess(char guess) {
        boolean correct = false;
        for (int i = 0; i < currentWord.length(); i++) {
            if (Character.toLowerCase(currentWord.charAt(i)) == Character.toLowerCase(guess)) {
                displayedWord.setCharAt(i, currentWord.charAt(i));
                correct = true;  // marks guess as correct
            }
        }
        if (!correct) {
            player.decreaseAttempts();  // decreases attempts if guess is wrong
            hangmanDrawing.updateDrawing();  // updates hangman drawing
            System.out.println("Incorrect");
        } else {
            System.out.println("Nice try!");
        }
        // checks if the game is over if there are no attempts left or the word is fully guessed
        if (player.getAttemptsLeft() == 0 || !displayedWord.toString().contains("_")) {
            gameOver = true;
        }
    }

    // returns the current displayed word with guessed letters and blanks
    private String getDisplayedWord() {
        return displayedWord.toString();
    }

    // handles word list options
    private void handleWordListOptions() {
        System.out.println("Word List Options:");
        System.out.println("1. Show me the word list!");
        System.out.println("2. Show me a specific word by line number");
        System.out.println("3. Update a word by line number");
        System.out.println("4. Remove a word by line number");
        System.out.print("Choose an option (1-4): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                wordList.displayWordListAsString();
                break;
            case "2":
                System.out.print("Enter the line number (1-5): ");
                int lineNumberToDisplay = Integer.parseInt(scanner.nextLine().trim());
                wordList.displaySpecificWord(lineNumberToDisplay);
                break;
            case "3":
                System.out.print("Enter a line number (1-5): ");
                int lineNumberToUpdate = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter a new word: ");
                String newWord = scanner.nextLine().trim();
                wordList.updateWordInList(lineNumberToUpdate, newWord);
                break;
            case "4":
                System.out.print("Enter the line number (1-5): ");
                int lineNumberToRemove = Integer.parseInt(scanner.nextLine().trim());
                wordList.removeWordFromList(lineNumberToRemove);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    // main method to start the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  
        HangmanGame game = new HangmanGame(scanner);
        game.start(new Statistics());
        scanner.close();  
    }
}
