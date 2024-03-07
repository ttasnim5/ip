package src.main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    /**
     * Read's the user's request from standard input.
     * Returns a string of the user's input.
     *
     * @return user input line.
     * @throws IOException If input stream is closed/ends unexpectedly, or becomes invalid or corrupted.
     */
    public static String readUserInputUnwrapped() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        return in.readLine().trim();
    }

    /**
     * Wrapper function for readUserInputUnwrapped(). Ensures ability to communicate and work with user input.
     * Returns a string of the user's input.
     * If an IOException is caught, the program terminates and returns null.
     *
     * @return user input line.
     */
    public static String readUserInput() {// wrapper function for reading user input
        try {
            return readUserInputUnwrapped().trim();
        } catch (IOException e) {
            System.out.println("» Sorry, something went wrong.\n");
            return null;
        }
    }
}
