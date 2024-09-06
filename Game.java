package hangmanproject;

public abstract class Game {
    protected boolean gameOver;  // flag that indicates if the game is over

    // method to start the game
    public abstract void start(Statistics statistics);

    // method to check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }
}
