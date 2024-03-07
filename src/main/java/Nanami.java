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
    private static boolean applicationOpen = true;

    public static void main(String args[]) {
        // driver code to begin the application

        Storage.loadData();
        // clear the screen of loading data
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Ui.giveIntroduction();

        while (applicationOpen) {
            String input = Parser.readUserInput();
            String [] commandWords = input.split("\\s+"); // cuts phrases at spaces into an array

            // one-word commands
            if (input.equals("bye") || input.equals("Bye")) {
                // if the command is exactly 'bye' or 'Bye'
                Ui.giveFarewell();
                Storage.closeFile();
                applicationOpen = false;

            } else if (input.equals("list") || input.equals("List")) {
                // if the command is exactly 'list' or 'List'
                TaskList.displayTaskList();
            }
            else if (commandWords[0].equals("mark") || commandWords[0].equals("unmark")) {
                if (commandWords.length != 2) {
                    Ui.unnumberedTask();
                } else {
                    TaskList.changeTaskMarker(commandWords);
                }
            } else if (commandWords[0].equals("delete")) {
                TaskList.deleteTask(commandWords[1]);
            } else {
                TaskList.addToList(input);
            }
        }
    }
}