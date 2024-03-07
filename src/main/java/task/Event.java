package src.main.java.task;

import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.EventMismatchedParameterException;

import java.util.Arrays;

public class Event extends Task {

    protected String from, to = "";

    public Event(String description, String from, String to) {
        // tasks that start at a specific date/time and ends at a specific date/time
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Verifies the validity of the provided user input and creates and returns the corresponding Event task object.
     * If the input is invalid (contains an empty description or mismatched parameters), the user is reprompted.
     *
     * @return the created Deadline object
     * @param userInputSeparatedBySlash String array containing the list of words inputted by user for this command.
     * @throws EventMismatchedParameterException if the given input does not include a singular /to attachment and a singular /from attachment.
     * @throws EmptyTaskException if the given input does not include description body for the Event task.
     */
    public Event(String[] userInputSeparatedBySlash) throws EventMismatchedParameterException, EmptyTaskException {
        super(userInputSeparatedBySlash[0].replace("event", "").trim());

        if(this.getDescription().isEmpty()) {
            throw new EmptyTaskException();
        } else if (userInputSeparatedBySlash.length > 3 || userInputSeparatedBySlash.length < 3) {
            // there's too many or not enough parameters, to/from or otherwise
            throw new EventMismatchedParameterException();
        } else if (userInputSeparatedBySlash[1].contains("from") && userInputSeparatedBySlash[2].contains("to")) {
            // from => to
            this.from = userInputSeparatedBySlash[1].replace("from", "").trim();
            this.to = userInputSeparatedBySlash[2].replace("to", "").trim();
        } else if (userInputSeparatedBySlash[1].contains("to") && userInputSeparatedBySlash[2].contains("from")) {
            this.to = userInputSeparatedBySlash[1].replace("to", "").trim();
            this.from = userInputSeparatedBySlash[2].replace("from", "").trim();
        } else {
            throw new EventMismatchedParameterException();
        }
    }

    /**
     * Returns the string representation of the Event task, including the task type, the status of the task, the description
     * and the times given by /to and /from.
     * Intended for viewing by the client.
     */
    @Override
    public String toString() {
        if (isDone){
            return ("[E]" + "[" + this.getStatusIcon() + "] " + super.getDescription() + " from:" + this.from + " to: " + this.to);
        } else {
            return ("[E]" + "[ ] " + super.getDescription() + " from: " + this.from + " to: " + this.to);
        }
    }

    /**
     * Returns the string representation of the Event task, including the task type, the description, the times
     * given by /to and /from, and the status of the task.
     * Intended for reading of data from the data file.
     */
    @Override
    public String sendToFile() {
        boolean isDone = true;
        if (this.getStatusIcon().equals(" ")) {
            isDone = false;
        }
        return "E | " + this.getDescription() + " | " + this.from + " | " + this.to + " | " + isDone;
    }
}
