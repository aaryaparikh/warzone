package DemoDriver;

import java.util.Scanner;

public class UserInputProcessor {
    public String processWithUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter something: ");
        String userInput = scanner.nextLine();
        System.out.println("You entered: " + userInput);
        return userInput;
    }
}