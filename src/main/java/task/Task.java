package src.main.java.task;

import src.main.java.taskExceptions.DeadlineMismatchedParameterException;
import src.main.java.taskExceptions.EmptyTaskException;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Returns the corresponding Task object with the given description.
     *
     * @return the created Task object
     * @param description String of words inputted by user for this command.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a String icon indicating the boolean status (done or not done) of the given task.
     *
     * @return the String icon or a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "¤" : " "); // mark done task with X
    }

    /**
     * Returns the String description of the given task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the given task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the given task as uncompleted.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the Task, including the status of the task and the description
     * Intended for viewing by the client.
     */
    public String toString() {
        if (isDone){
            return ("[ ]" + "[" + this.getStatusIcon() + "] " + this.description);
        } else {
            return ("[ ]" + "[ ] " + this.description);
        }
    }

    /**
     * Returns the string representation of the Task, including the description and the status of the task.
     * Intended for reading of data from the data file.
     */
    public String sendToFile() {
        return description + " | " + isDone;
    }
}
