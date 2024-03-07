package src.main.java;

import src.main.java.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ui {

    static void giveIntroduction() {
        // print a visual introduction when application is open
        System.out.println("\n\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»");
        System.out.println("\t|\\    |   /\\     |\\    |   /\\     | \\     /|  |");
        System.out.println("\t|  \\  |  /---\\   |  \\  |  /---\\   |   \\  / |  |");
        System.out.println("\t|    \\| /     \\  |    \\| /     \\  |    \\/  |  |");
        System.out.println("\t» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «» ¤ «»\n");
        System.out.println("» Hello. I am Nanami, your task-manager.\n» What can I do for you?\n");
    }

    public static void giveFarewell() {
        // print a farewell to user
        System.out.println("» Until next time. Goodbye.");
    }

    public static void invalidToDo() {
        System.out.println("I can't store a todo like that. It only needs a description, no attachments using /.");
    }

    public static void invalidDeadline() {
        System.out.println("The deadline isn't right. I need the task type, description, and /by attachment. Try again.");
    }

    public static void invalidEvent() {
        System.out.println("An event has a /to attachment and a /from attachment only. I can't store it like this.");
    }

    public static void invalidTask() {
        System.out.println("I'm sorry, can you repeat that with the corresponding task type?\nYou can do a todo task, a deadline task, or an event.");
    }

    public static void unrecordedTask() {
        System.out.println("I don't have that task on file. You can try again.");
    }

    public static void unnumberedTask() {
        System.out.println("You just need a task number with that order.");
    }

    public static void deletedTask(Task currTask) {
        System.out.println("Okay I've scarpped" + currTask.toString());
    }

    public static void emptyList() {
        System.out.println("You currently have no tasks in your list. Congrats.");
    }

    public static void updatedList() {
        System.out.println("This is your most recently updated list: ");
        Task[] tasklist = TaskList.getTaskList();
        int taskcount = TaskList.getTaskCount();
        for (int i = 0; i < taskcount; i++) {
            System.out.println("\t" + i + " " + tasklist[i].toString());
        }
    }
}