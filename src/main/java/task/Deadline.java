package src.main.java.task;

import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;

import java.util.Arrays;

/**
 * Represents a task that is due by a certain deadline.
 */
public class Deadline extends Task {

    protected String by = "";

    /**
     * Verifies the validity of the provided user input and creates and returns the corresponding Deadline task object.
     * If the input is invalid (contains an empty description or mismatched parameters), the user is reprompted.
     *
     * @return the created Deadline object
     * @param userInputSeparatedBySlash String array containing the list of words inputted by user for this command.
     * @throws DeadlineMismatchedParameterException if the given input does not include a singular /by attachment
     * @throws EmptyTaskException if the given input does not include description body for the Deadline task
     */
    public Deadline(String[] userInputSeparatedBySlash) throws DeadlineMismatchedParameterException, EmptyTaskException {
        super(userInputSeparatedBySlash[0].replace("deadline", "").trim());

        if (this.getDescription().isEmpty()) {
            throw new EmptyTaskException();
        } else if (userInputSeparatedBySlash.length == 1) { // no parameters given
            throw new DeadlineMismatchedParameterException();
        } else if (userInputSeparatedBySlash.length > 2) { // there's multiple parameters, /by or otherwise
            throw new DeadlineMismatchedParameterException();
        } else if (!userInputSeparatedBySlash[1].contains("by")) { // if the singular parameter is not by
            throw new DeadlineMismatchedParameterException();
        }

        this.by = userInputSeparatedBySlash[1].replace("by", "").trim();
    }

    /**
     * Returns the string representation of the Deadline task, including the task type, the status of the task, the description
     * and the deadline given by /by.
     * Intended for viewing by the client.
     */
   @Override
    public String toString() {
        if (isDone){
            return ("[D]" + "[" + this.getStatusIcon() + "] " + super.getDescription() + " by: " + this.by);
        } else {
            return ("[D]" + "[ ] " + super.getDescription() + " by: " + this.by);
        }
    }

    /**
     * Returns the string representation of the Deadline task, including the task type, the description, the deadline
     * given by /by, and the status of the task.
     * Intended for reading of data from the data file.
     */
    @Override
    public String sendToFile() {
        boolean isDone = true;
        if (this.getStatusIcon().equals(" ")) {
            isDone = false;
        }
        return "D | " + this.getDescription() + " | " + this.by + " | " + isDone;
    }
}
