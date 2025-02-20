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

public class TaskList {

    /**
     * Iterates through the current list of tasks and prints any tasks that contains the provided keywords.
     * If no tasks contain the keywords, prints that no tasks match.
     *
     * @param keywords description that the user is searching for.
     */
    public static void find(Task[] tasklist, int taskcount, String keywords) {
        System.out.println("» Let's see...");
        boolean found = false;
        for (int i = 0; i < taskcount; i++) {
            if (tasklist[i].getDescription().contains(keywords)) {
                System.out.println(i + " " + tasklist[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("» I don't know about that one. Can't find it anywhere.");
        }
    }
}
