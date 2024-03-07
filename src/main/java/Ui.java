package src.main.java;

import src.main.java.task.Task;

public class Ui {

    /**
     * Prints the opening logo for the Nanami application, with a welcome message.
     */
    public static void giveIntroduction() {
        // print a visual introduction when application is open
        System.out.println("\n\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»");
        System.out.println("\t|\\    |   /\\     |\\    |   /\\     | \\     /|  |");
        System.out.println("\t|  \\  |  /---\\   |  \\  |  /---\\   |   \\  / |  |");
        System.out.println("\t|    \\| /     \\  |    \\| /     \\  |    \\/  |  |");
        System.out.println("\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»\n");
        System.out.println("» Hello. I am Nanami, your task-manager.\n» What can I do for you?\n");
    }

    /**
     * Prints the contents of the current list of tasks.
     * If the list is empty, the user is informed and reprompted.
     *
     * @param tasklist List of tasks.
     * @param taskcount number of tasks in the list.
     */
    public static void displayTaskList(Task[] tasklist, int taskcount) {
        if (taskcount == 0) {
            System.out.println("» You have no tasks in your list currently.");
        } else {
            System.out.println("» This is your most recently updated tasklist: ");
            for (int i = 0; i < taskcount; i++) {
                System.out.println("\t" + i + " " + tasklist[i].toString());
            }
        }
    }
}
