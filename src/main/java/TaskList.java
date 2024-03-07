package src.main.java;

import src.main.java.task.Deadline;
import src.main.java.task.Event;
import src.main.java.task.Task;
import src.main.java.task.ToDo;
import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.EventMismatchedParameterException;
import src.main.java.taskExceptions.ToDoMismatchedParameterException;

import java.io.IOException;

import static src.main.java.Storage.updateFile;

public class TaskList {
    private static Task[] tasklist = new Task[100];
    private static int taskcount;
    private int taskCount = 0;
    public TaskList() {

    }

    public static void addToList(String input) {
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
            } catch (EmptyTaskException | ToDoMismatchedParameterException e) {
                Ui.invalidToDo();
           } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else if (inputArray[0].equals("deadline")) {
            try {
                Deadline newTask = new Deadline(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException | DeadlineMismatchedParameterException e) {
                Ui.invalidDeadline();
            } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else if (inputArray[0].equals("event")) {
            try {
                Event newTask = new Event(input.split("/"));
                tasklist[taskcount++] = newTask;
                updateFile(1);
            } catch (EmptyTaskException | EventMismatchedParameterException e) {
                Ui.invalidEvent();
            } catch (IOException e) {
                System.out.println("no save file :(");
            }
        } else {
            Ui.invalidTask();
        }

    }

    public static void displayTaskList() {
        if (taskcount == 0) {
            Ui.emptyList();
        } else {
            Ui.updatedList();
        }
    }

    public static void changeTaskMarker(String[] commandWords) {
        try {
            int taskNumber = Integer.parseInt(commandWords[1]);
            if (taskNumber >= taskcount || taskNumber < 0) { // if the task does not
                Ui.unrecordedTask();
                return;
            }

            if (commandWords[0].equals("mark")) {
                tasklist[taskNumber].markAsDone();
                System.out.println("I've marked [" + taskNumber + "] as completed.");
            } else {
                tasklist[taskNumber].markAsUndone();
                System.out.println("I've marked [" + taskNumber + "] as uncompleted.");
            }
            updateFile(1);
            displayTaskList();
        } catch (NumberFormatException e) {
            Ui.unnumberedTask();
        } catch (IOException e) {
            System.out.println("no save file :(");
        }
    }

    public static void deleteTask(String word) {
        try {
            int taskNumber = Integer.parseInt(word);
            if (taskNumber > taskcount || taskNumber < 0) { // if the task does not exist in the tasklist
                Ui.unrecordedTask();
                return;
            }

            for (int i = taskNumber; i < taskcount; i++) {
                tasklist[i] = tasklist[i + 1];
            }
            Task currTask = tasklist[taskNumber];
            taskcount--;
            updateFile(0);
            Ui.deletedTask(currTask);
            displayTaskList();
        } catch (NumberFormatException e){
            System.out.println("If you want me to delete a task, I need a task number with it.");
        } catch (IOException e) {
            System.out.println("no save file :(");
        }
    }

    public static Task[] getTaskList() {
        return tasklist;
    }

    public static int getTaskCount() {
        return taskcount;
    }
}
