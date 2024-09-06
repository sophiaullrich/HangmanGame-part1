package hangmanproject;

public class HangmanDrawing {
    private int wrongGuesses = 0; // number of incorrect guesses

    // adds wrong guesses and draws hangman
    public void updateDrawing() {
        wrongGuesses++;
        draw(); // draws current hangman state
    }

    // draws the hangman based on the number of wrong guesses
    private void draw() {
        switch (wrongGuesses) {
            case 1:
                System.out.println(" O "); // head
                break;
            case 2:
                System.out.println(" O "); 
                System.out.println(" | "); // body
                break;
            case 3:
                System.out.println(" O "); 
                System.out.println("/| "); // one arm
                break;
            case 4:
                System.out.println(" O "); 
                System.out.println("/|\\ "); // two arms
                break;
            case 5:
                System.out.println(" O "); 
                System.out.println("/|\\ "); 
                System.out.println("/  "); // one leg
                break;
            case 6:
                System.out.println(" O "); 
                System.out.println("/|\\ "); 
                System.out.println("/ \\ "); // two legs
                System.out.println("Game over!"); // game over message
                break;
        }
    }

    // checks if the hangman is fully drawn 
    public boolean isComplete() {
        return wrongGuesses >= 6;
    }
}

