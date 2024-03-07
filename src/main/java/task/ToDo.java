package src.main.java.task;

import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.ToDoMismatchedParameterException;

import java.util.Arrays;


public class ToDo extends Task {

    /**
     * Verifies the validity of the provided user input and creates and returns the corresponding ToDo task object.
     * If the input is invalid (contains an empty description or any parameters), the user is reprompted.
     *
     * @return the created Deadline object
     * @param userInputSeparatedBySlash String array containing the list of words inputted by user for this command.
     * @throws ToDoMismatchedParameterException if the given input includes any attachments
     * @throws EmptyTaskException if the given input does not include description body for the ToDo task
     */
    public ToDo(String[] userInputSeparatedBySlash) throws ToDoMismatchedParameterException, EmptyTaskException {
        super(userInputSeparatedBySlash[0].replace("todo", "").trim());

        if (this.getDescription().isEmpty()) {
            throw new EmptyTaskException();
        } else if (userInputSeparatedBySlash.length > 1) {
            throw new ToDoMismatchedParameterException();
        }
    }

    /**
     * Returns the string representation of the ToDo task, including the task type, the status of the task, and
     * the description.
     * Intended for viewing by the client.
     */
    @Override
    public String toString() {
        if (isDone){
            return ("[T]" + "[" + this.getStatusIcon() + "] " + super.getDescription());
        } else {
            return ("[T]" + "[ ] " + super.getDescription());
        }
    }

    /**
     * Returns the string representation of the ToDo task, including the task type, the description, and
     * the status of the task.
     * Intended for reading of data from the data file.
     */
    @Override
    public String sendToFile() {
        boolean isDone = true;
        if (this.getStatusIcon().equals(" ")) {
            isDone = false;
        }
        return "T | " + this.getDescription() + " | " + isDone;
    }
}
