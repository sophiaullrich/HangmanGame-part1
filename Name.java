package hangmanproject;

import java.util.Scanner;

public class Name {
    private String name; // stores the user's name

    // constructor that prompts the user to enter their name
    public Name(Scanner scanner) {
        System.out.print("Please enter your nickname: ");
        this.name = scanner.nextLine().trim(); // store the entered name after trimming whitespace
    }

    // getter method to retrieve the user's name
    public String getName() {
        return name;
    }

    // setter method to update the user's name if needed
    public void setName(String name) {
        this.name = name;
    }
}
