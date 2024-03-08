package src.main.java;

import src.main.java.task.Task;

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
