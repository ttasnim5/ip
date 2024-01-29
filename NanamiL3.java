import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import packages to read user input from console

public class NanamiL3 {
    private static Task[] tasklist = new Task[100];
    private static int taskcount;
    private int taskCount = 0;
    private static boolean applicationOpen = true;

    public static void main(String args[]) {
        // driver code to begin the application
        giveIntroduction();

        while (applicationOpen) {
            String input = readUserInput();
            String [] commandWords = input.split("\\s+"); // cuts phrases at spaces into an array

            // perform actions based on user input
            if (input.equals("bye") || input.equals("Bye")) {
                // if the command is exactly 'bye' or 'Bye'
                giveFarewell();
                applicationOpen = false;
            }
            else if (input.equals("list") || input.equals("List")) {
                // if the command is exactly 'list' or 'List'
                displayTaskList();
            }
            else if (commandWords[0].equals("mark") || commandWords[0].equals("Mark")) {
                int taskNumber = checkIfNumber(commandWords[1]);
                // if the first word is mark and the second word is a number
                if (taskNumber < taskcount) { // if the task exists in the tasklist
                    tasklist[taskNumber].markAsDone();
                    System.out.println("I've marked [" + taskNumber + "] as completed.");
                }
                else {
                    System.out.println("» This task number does not exist.");
                }
                displayTaskList();
            }
            else if (commandWords[0].equals("unmark") || commandWords[0].equals("Unmark")) {
                int taskNumber = checkIfNumber(commandWords[1]);
                // if the first word is unmark and the second word is a number
                if (taskNumber < taskcount) { // if the task exists in the tasklist
                    tasklist[taskNumber].markAsUndone();
                    System.out.println("I've marked [" + taskNumber + "] as incompleted.");
                }
                else {
                    System.out.println("» This task number does not exist.");
                }
                displayTaskList();
            }
            else {
                addToList(input);
            }
        }
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
        tasklist[taskcount++] = new Task(input);
        System.out.println("» I have added to your tasklist: [" + (taskcount - 1) + "] " + input); // echo contents of tasklist back to user
    }

    private static String readUserInputUnwrapped() throws IOException {
        // internal function to read a user's input, process the meaning, and call functions accordingly
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine().trim(); // trim takes care of trailing spaces
        return input;
    }

    private static String readUserInput() {// wrapper function for reading user input
        try {
            String input = readUserInputUnwrapped();
            return input;
        } catch (IOException e) {
            System.out.println("» Sorry, something went wrong.\n» Try again.");
            return null;
        }
    }

    private static void displayTaskList() {
        if (taskcount == 0) {
            System.out.println("» You have no tasks in your list currently.");
        }
        else {
            System.out.println("» This is your most recently updated tasklist: ");
            for (int i = 0; i < taskcount; i++) {
                Task task = tasklist[i];
                System.out.println("\t" + i + " [" + task.getStatusIcon() + "] " + task.getDescription());
            }
        }
    }

    private static int checkIfNumber(String input) throws NumberFormatException {
        int taskNumber = taskcount + 1; // default it to not be a valid task number by the tasklist
        try {
            taskNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            addToList("mark "+ input);
        }
        return taskNumber;
    }
}