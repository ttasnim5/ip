package src.main.java;

import src.main.java.task.Deadline;
import src.main.java.task.Event;
import src.main.java.task.Task;
import src.main.java.task.ToDo;
import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.EventMismatchedParameterException;
import src.main.java.taskExceptions.ToDoMismatchedParameterException;

/**
 * Creates an instance of the Nanami class, beginning the application for a user.
 */
public class Nanami {
    private static final int numTasks = 100;
    private static Task[] tasklist = new Task[numTasks];
    private static int taskcount;
    private static boolean applicationOpen = true;
    private static final String filepath = "taskDrive.txt";
    private static File outfile = new File(filepath);
    private static FileWriter overwriter;
    private static FileWriter appender;
    public static void main(String args[]) {
        loadData();
        // clear the screen of loading data
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Ui.giveIntroduction();

        while (applicationOpen) {
            String input = Parser.readUserInput();
            String [] commandWords = input.split("\\s+");
            // cuts phrases at spaces into an array
            if (input == null) {
                applicationOpen = false;
            } else if (input.isEmpty()) {
                System.out.println("» You gotta give me something to work with here.");
            }

            // one-word commands
            if (input.equals("bye") || input.equals("Bye")) {
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
                Ui.displayTaskList(tasklist, taskcount);
            } else if (commandWords[0].equals("mark") || commandWords[0].equals("unmark")) {
                if (commandWords.length != 2) {
                    System.out.println("» You just need a task number with that order.");
                } else {
                    changeTaskMarker(commandWords);
                }
            } else if (commandWords[0].equals("delete")) {
                deleteTask(commandWords[1]);
            } else if (commandWords[0].equals("find") || commandWords[0].equals("Find")) {
                TaskList.find(tasklist, taskcount, input.substring(4).trim());
            } else {
                addToList(input);
            }
        }
    }

    /**
     * Verifies the validity of the provided task number and checks or unchecks
     * it from the list and memory.If the number is invalid (not part of the
     * list of tasks or an empty string), the user is reprompted. If the method
     * cannot access the data file properly to make changes, the application terminates.
     *
     * @param commandWords String array containing the list of words inputted by user for
     *                     this command.
     */
    public static void changeTaskMarker(String[] commandWords) {
        try {
            int taskNumber = Integer.parseInt(commandWords[1]);
            if (taskNumber >= taskcount || taskNumber < 0) {
                System.out.println("» That task isn't on record. Look at it again.");
                return;
            }

            if (commandWords[0].equals("mark")) {
                tasklist[taskNumber].markAsDone();
                System.out.println("» I've marked [" + taskNumber + "] as completed.");
            } else {
                tasklist[taskNumber].markAsUndone();
                System.out.println("» I've marked [" + taskNumber + "] as uncompleted.");
            }
            updateFile(0);
            Ui.displayTaskList(tasklist, taskcount);
        } catch (NumberFormatException e) {
            System.out.println("» If you need me to mark or unmark a task, I need " +
                    "a task number right after the command word.");
        } catch (IOException e) {
            System.out.println("» no save file :(");
        }
    }

    /**
     * Prints a goodbye message to the user.
     * Copies the most-recently updated list into memory.
     * If the method cannot access the data file properly to make
     * changes, it is indicated to the user and the application terminates.
     */
    public static void giveFarewell() {
        System.out.println("» Until next time. Goodbye.");

        try {
            appender = new FileWriter(outfile, true);
            appender.write("xx"); // text marker for text analysis purposes
        } catch (IOException e) {
            System.out.println("» could not end the file properly");
            applicationOpen = false;
        }
    }

    /**
     * Updates the list of tasks saved in the data file.
     *
     * @param mode 0 or 1, indicates whether to append to the current list or
     *             rewrite it from the beginning.
     * @throws IOException if the data file cannot be found, is invalid, or is
     * corrupted.
     */
    public static void updateFile(int mode) throws IOException {
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

    /**
     * Repopulates the new list of tasks with tasks from the previous session when the
     * application is first opened. The data file is rewritten at the end of this
     * function to prevent duplicates and ensure a clean list in the file.
     * If there is an issue reading the data from the file or rewriting to the file,
     * the user is informed and the application terminates.
     */
    public static void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("taskDrive.txt"));
            String line = reader.readLine();
            int count = 0, doneIndex = 0;

            while ( line != null && !line.equals("xx")) {
                String[] pastData = line.split("\\|");

                String tasktype = pastData[0].trim();
                if (tasktype.equals("T")) {
                    addToList("todo " + pastData[1]);
                    doneIndex = 2;
                } else if (tasktype.equals("D")) {
                    addToList("deadline " + pastData[1] + " /by " + pastData[2]);
                    doneIndex = 3;
                } else if (tasktype.equals("E")) {
                    addToList("event " + pastData[1] + " /from " + pastData[2] +
                            " /to " + pastData[3]);
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
            try { // new user
                outfile.createNewFile();
            } catch (IOException ex) {
                System.out.println("something's wrong :0");
                applicationOpen = false;
            }
        } catch (IOException e) {
            System.out.println("\tio exception\t");
            applicationOpen = false;
        }
    }

    /**
     * Searches for the task corresponding with the provided task number and
     * removes it from the list and memory. If the task number indicated is
     * not in the list of tasks, the user is reprompted. If the method cannot
     * access the data file properly to make changes, the user is reprompted.
     *
     * @param word String representation of the task number that user wants to delete.
     */
    public static void deleteTask(String word) {
        try {
            int taskNumber = Integer.parseInt(word);
            if (taskNumber > taskcount || taskNumber < 0) { // if the task does not exist in the tasklist
                System.out.println("» That task isn't on record. Look at it again.");
                return;
            }
            Task currTask = tasklist[taskNumber];
            for (int i = taskNumber; i < taskcount; i++) {
                tasklist[i] = tasklist[i + 1];
            }
            taskcount--;
            updateFile(0);
            System.out.println("» Okay, I've scrapped " + currTask.toString());
            Ui.displayTaskList(tasklist, taskcount);
        } catch (NumberFormatException e) {
            System.out.println("» If you want me to delete a task, I need a task number with it.");
        } catch (IOException e) {
            System.out.println("» no save file :(");
        }
    }

    /**
     * Verifies the validity of the desired task and adds it to the list of tasks and memory.
     * If the desired task is invalid (does not match the ToDo, Deadline, or Event task types,
     * or has mismatched parameters), the user is reprompted.
     * If the method cannot access the data file properly to make changes, the application
     * terminates.
     *
     * @param input text given directly from the user for this command
     */
    public static void addToList(String input) {
        if (input.isEmpty()) {
            return;
        }
        String[] inputArray = input.split("\\s+");
        Task newTask = null;
        if (inputArray[0].equals("todo")) {
            try {
                newTask = new ToDo(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("» I can't store a todo like that. It only needs a description, no attachments using /.");
            } catch (ToDoMismatchedParameterException e) {
                System.out.println("» A todo task does not have any attachments using /. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("» no save file :(");
            }
        } else if (inputArray[0].equals("deadline")) {
            try {
                newTask = new Deadline(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("» The deadline is missing something. I need the task type, description, and /by attachment. Try again.");
            } catch (DeadlineMismatchedParameterException e) {
                System.out.println("» A deadline has a /by attachment only. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("» no save file :(");
            }
        } else if (inputArray[0].equals("event")) {
            try {
                newTask = new Event(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException e) {
                System.out.println("» The event is missing information. I need the task type, description, and /to and /from attachments. Try again.");
            } catch (EventMismatchedParameterException e) {
                System.out.println("» An event has a /to attachment and a /from attachment only. I can't store it like this.");
            } catch (IOException e) {
                System.out.println("» no save file :(");
            }
        } else {
            System.out.println("» I'm sorry, can you repeat that with the corresponding task type?\nYou can do a todo task, a deadline task, or an event.");
            return;
        }
        System.out.println("» Added to your list: " + newTask);
    }
}
