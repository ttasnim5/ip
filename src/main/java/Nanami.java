package src.main.java;

import src.main.java.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Nanami {
    private static Task[] tasklist = new Task[100];
    private static int taskcount;
    private int taskCount = 0;
    private static boolean applicationOpen = true;

    public static void main(String args[]) {
        // driver code to begin the application
        giveIntroduction();

        while (applicationOpen) {
            String input = readUserInput().trim();
            String [] commandWords = input.split("\\s+"); // cuts phrases at spaces into an array

            if (input.equals("")) { // check for empty string
                System.out.println("You gotta give me something to work with here.");
            }

            // one-word commands
            if (input.equals("bye") || input.equals("Bye")) {
                // if the command is exactly 'bye' or 'Bye'
                giveFarewell();
                applicationOpen = false;
            }
            else if (input.equals("list") || input.equals("List")) {
                // if the command is exactly 'list' or 'List'
                displayTaskList();
            }
            else if (commandWords[0].equals("mark") || commandWords[0].equals("unmark")) {
                changeTaskMarker(commandWords);
            }
            else {
                addToList(input);
            }
        }
    }

    private static void changeTaskMarker(String[] commandWords) {
        try {
            int taskNumber = Integer.parseInt(commandWords[1]);
            if (taskNumber > taskcount || taskNumber < 0) { // if the task does not exist in the tasklist
                System.out.println("That task isn't on record. Look at it again.");
                return;
            }

            if (commandWords[0].equals("mark")) {
                tasklist[taskNumber].markAsDone();
                System.out.println("I've marked [" + taskNumber + "] as completed.");
            }
            else {
                tasklist[taskNumber].markAsUndone();
                System.out.println("I've marked [" + taskNumber + "] as uncompleted.");
            }
            displayTaskList();
        }
        catch (NumberFormatException e){
            System.out.println("If you need me to mark or unmark a task, I need a task number right after the command word.");
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
        System.out.println("» Hello. I am src.main.java.Nanami, your task-manager.\n» What can I do for you?\n");
    }

    private static void giveFarewell() {
        // print a farewell to user
        System.out.println("» Until next time. Goodbye.");
    }

    private static void addToList(String input) {
        // add user's input to an array of tasks
        if (input.isEmpty()) {
            return;
        }
        String[] inputArray = input.split("\\s+");

        if (inputArray[0].equals("todo")) {
            try {
                ToDo newTask = new ToDo(input.split("/"));
                tasklist[taskcount++] = newTask;
            } catch (EmptyTaskException e) {
                System.out.println("I can't store a todo like that. It only needs a description, no attachments using /.");
            } catch (ToDoMismatchedParameterException e) {
                System.out.println("A todo task does not have any attachments using /. I can't store it like this.");
            }
        }
        else if (inputArray[0].equals("deadline")) {
            try {
                Deadline newTask = new Deadline(input.split("/"));
                tasklist[taskcount++] = newTask;
            } catch (EmptyTaskException e) {
                System.out.println("The deadline is missing something. I need the task type, description, and /by attachment. Try again.");
            } catch (DeadlineMismatchedParameterException e) {
                System.out.println("A deadline has a /by attachment only. I can't store it like this.");
            }
        }
        else if (inputArray[0].equals("event")) {
            try {
                Event newTask = new Event(input.split("/"));
                tasklist[taskcount++] = newTask;
            } catch (EmptyTaskException e) {
                System.out.println("The event is missing information. I need the task type, description, and /to and /from attachments. Try again.");
            } catch (EventMismatchedParameterException e) {
                System.out.println("An event has a /to attachment and a /from attachment only. I can't store it like this.");
            }
        }
        else {
            System.out.println("I'm sorry, can you repeat that with the corresponding task type?\nYou can do a todo task, a deadline task, or an event.");
        }
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
                System.out.println("\t" + i + " " + tasklist[i].toString());
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