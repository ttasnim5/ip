package src.main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {

    private static String readUserInputUnwrapped() throws IOException {
        // internal function to read a user's input, process the meaning, and call functions accordingly
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine().trim();
        return input;
    }

    public static String readUserInput() {// wrapper function for reading user input
        try {
            String input = readUserInputUnwrapped();
            return input.trim();

        } catch (IOException e) {
            System.out.println("» Sorry, something went wrong.\n» Try again.");
            return null;
        }
    }
}
