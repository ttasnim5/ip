package src.main.java;

import src.main.java.task.Deadline;
import src.main.java.task.Event;
import src.main.java.task.Task;
import src.main.java.task.ToDo;
import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.EventMismatchedParameterException;
import src.main.java.taskExceptions.ToDoMismatchedParameterException;

import java.io.*;
import java.util.Arrays;

public class Nanami {
    private static Task[] tasklist = new Task[100];
    private static int taskcount;
    private int taskCount = 0;
    private static boolean applicationOpen = true;
    private static String filepath = "taskDrive.txt";
    private static File outfile = new File(filepath);
    private static FileWriter overwriter, appender;

    public static void main(String args[]) {
        // driver code to begin the application
        loadData();
        // clear the screen of loading data
        System.out.print("\033[H\033[2J");
        System.out.flush();

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
                try {
                    updateFile(0);
                    appender = new FileWriter(outfile, true);
                    appender.write("xx\n");
                    appender.flush();
                    appender.close();
                } catch (IOException e) {
                    System.out.println("proper file closing failed lol");
                }
            } else if (input.equals("list") || input.equals("List")) {
                // if the command is exactly 'list' or 'List'
                displayTaskList();
            }
            else if (commandWords[0].equals("mark") || commandWords[0].equals("unmark")) {
                if (commandWords.length != 2) {
                    System.out.println("You just need a task number with that order.");
                } else {
                    changeTaskMarker(commandWords);
                }
            } else if (commandWords[0].equals("delete")) {
                deleteTask(commandWords[1]);
            } else {
                addToList(input);
            }
        }
    }

    private static void deleteTask(String word) {
        try {
            int taskNumber = Integer.parseInt(word);
            if (taskNumber > taskcount || taskNumber < 0) { // if the task does not exist in the tasklist
                System.out.println("That task isn't on record. Look at it again.");
                return;
            }

            for (int i = taskNumber; i < taskcount; i++) {
                tasklist[i] = tasklist[i + 1];
            }
            Task currTask = tasklist[taskNumber];
            taskcount--;
            updateFile(0);
            System.out.println("Okay, I've scrapped " + currTask.toString());
            displayTaskList();
        } catch (NumberFormatException e){
            System.out.println("If you want me to delete a task, I need a task number with it.");
        } catch (IOException e) {
            System.out.println("no save file :(");
        }
    }

    private static void changeTaskMarker(String[] commandWords) {
        try {
            int taskNumber = Integer.parseInt(commandWords[1]);
            if (taskNumber >= taskcount || taskNumber < 0) { // if the task does not exist in the tasklist
                System.out.println("That task isn't on record. Look at it again.");
                return;
            }

            if (commandWords[0].equals("mark")) {
                tasklist[taskNumber].markAsDone();
                System.out.println("I've marked [" + taskNumber + "] as completed.");
            } else {
                tasklist[taskNumber].markAsUndone();
                System.out.println("I've marked [" + taskNumber + "] as uncompleted.");
            }
            updateFile(0);
            displayTaskList();
        } catch (NumberFormatException e){
            System.out.println("If you need me to mark or unmark a task, I need a task number right after the command word.");
        } catch (IOException e) {
            System.out.println("no save file :(");
        }
    }

    private static void giveIntroduction() {
        // print a visual introduction when application is open
        System.out.println("\n\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»");
        System.out.println("\t|\\    |   /\\     |\\    |   /\\     | \\     /|  |");
        System.out.println("\t|  \\  |  /---\\   |  \\  |  /---\\   |   \\  / |  |");
        System.out.println("\t|    \\| /     \\  |    \\| /     \\  |    \\/  |  |");
        System.out.println("\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»\n");
        System.out.println("» Hello. I am Nanami, your task-manager.\n» What can I do for you?\n");
    }

    private static void giveFarewell() {
        // print a farewell to user
        System.out.println("» Until next time. Goodbye.");

        try {
            appender = new FileWriter(outfile, true);
            appender.write("xx");
        } catch (IOException e) {
            System.out.println("could not end the file properly");
        }
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
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("I can't store a todo like that. It only needs a description, no attachments using /.");
            } catch (ToDoMismatchedParameterException e) {
                System.out.println("A todo task does not have any attachments using /. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else if (inputArray[0].equals("deadline")) {
            try {
                Deadline newTask = new Deadline(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("The deadline is missing something. I need the task type, description, and /by attachment. Try again.");
            } catch (DeadlineMismatchedParameterException e) {
                System.out.println("A deadline has a /by attachment only. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else if (inputArray[0].equals("event")) {
            try {
                Event newTask = new Event(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("The event is missing information. I need the task type, description, and /to and /from attachments. Try again.");
            } catch (EventMismatchedParameterException e) {
                System.out.println("An event has a /to attachment and a /from attachment only. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else {
            System.out.println("I'm sorry, can you repeat that with the corresponding task type?\nYou can do a todo task, a deadline task, or an event.");
        }
    }

    private static String readUserInputUnwrapped() throws IOException {
        // internal function to read a user's input, process the meaning, and call functions accordingly
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine().trim();
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
        } else {
            System.out.println("» This is your most recently updated tasklist: ");
            for (int i = 0; i < taskcount; i++) {
                System.out.println("\t" + i + " " + tasklist[i].toString());
            }
        }
    }

    private static void updateFile(int mode) throws IOException {
        if (mode == 1) { // just adding to the list
            appender = new FileWriter(outfile, true);
            appender.write(tasklist[taskcount - 1].sendToFile() + "\n");
            appender.flush();
            appender.close();
        } else { // in the case of deleting a task, marking a task as done/undone
            overwriter = new FileWriter(outfile, false);
            for (int i = 0; i < taskcount; i++) {
                overwriter.write(tasklist[i].sendToFile() + "\n");
                overwriter.flush();
            }
            overwriter.close();
        }
    }

    private static void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("taskDrive.txt"));
            String line = reader.readLine();
            int count = 0, doneIndex = 0;

            while ( line != null && ! line.equals("xx")) {
                String[] pastData = line.split("\\|");

                String tasktype = pastData[0].trim();
                if (tasktype.equals("T")) {
                    addToList("todo " + pastData[1]);
                    doneIndex = 2;
                } else if (tasktype.equals("D")) {
                    addToList("deadline " + pastData[1] + " /by " + pastData[2]);
                    doneIndex = 3;
                } else if (tasktype.equals("E")) {
                    addToList("event " + pastData[1] + " /from " + pastData[2] + " /to " + pastData[3]);
                    doneIndex = 4;
                }

                if (pastData[doneIndex].trim().equals("true")) {
                    String [] commandWords = new String[2];
                    commandWords[0] = "mark"; commandWords[1] = Integer.toString(count);
                    changeTaskMarker(commandWords);
                }

                count++;
                line = reader.readLine();
            }
            reader.close();
            // clear out the file to get rid of the old data and prevent repeats
            overwriter = new FileWriter(outfile, false);
            overwriter.write("");
            overwriter.flush();
            overwriter.close();

            // rewrite the present list into taskDrive.txt
            updateFile(0);
        } catch (FileNotFoundException e) {
            System.out.println("\tfile not found\t");
        } catch (IOException e) {
            System.out.println("\tio exception\t");
        }
    }

    private static int checkIfNumber(String input) throws EmptyTaskException {
        int taskNumber = taskcount + 1; // default it to not be a valid task number by the tasklist
        try {
            taskNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new EmptyTaskException();
        }
        return taskNumber;
    }
}