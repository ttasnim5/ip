import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import packages to read user input from console

public class NanamiL2 {
    private static String[] tasklist = new String[100];
    private static int taskcount;
    private int taskCount = 0;

    public static void main(String args[]) {
        // driver code to beign the application
        giveIntroduction();
        processUserInputWrapper();
    }

    private static void giveIntroduction() {
        // print a visual introduction when application is open
        System.out.println("\n\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»");
        System.out.println("\t|\\    |   /\\     |\\    |   /\\     | \\     /|  |");
        System.out.println("\t|  \\  |  /---\\   |  \\  |  /---\\   |   \\  / |  |");
        System.out.println("\t|    \\| /     \\  |    \\| /     \\  |    \\/  |  |");
        System.out.println("\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»\n");

        // introduce nanami as a task manager
        System.out.println("» Hello. I am Nanami, your task-manager.\n» What can I do for you?\n");
    }

    private static void giveFarewell() {
        // print a farewell to user
        System.out.println("» Until next time. Goodbye.");
    }

    private static void addToList(String input) {
        // add user's input to an array of tasks
        tasklist[taskcount++] = input;
        System.out.println("» added: "+ input); // echo contents of tasklist back to user
    }

    private static void processUserInput() throws IOException {
        // internal function to read a user's input, process the meaning, and call functions accordingly
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine();

        if (input.equals("bye") || input.equals("Bye")) {
            giveFarewell();
        }
        else if (input.equals("list") || input.equals("List")) {
            displayTaskList();
        }
        else {
            addToList(input);
            processUserInputWrapper();
        }
    }

    private static void processUserInputWrapper() {
        // wrapper class that calls processUserInput() with  try catch block for exceptions
        try {
            processUserInput();
        } catch (IOException e) {
            System.out.println("Sorry, I didn't catch that.\n» Try again.");
            processUserInputWrapper();
        }
    }

    private static void displayTaskList() {
        for (int i = 0; i < taskcount; i++) {
            System.out.println("[" + i + "] " + tasklist[i]);
        }
        processUserInputWrapper(); // go back to reading user input
    }
}