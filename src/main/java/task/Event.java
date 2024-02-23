package src.main.java.task;

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

    @Override
    public String toString() {
        if (isDone){
            return ("[E]" + "[" + this.getStatusIcon() + "] " + super.getDescription() + " from:" + this.from + " to: " + this.to);
        } else {
            return ("[E]" + "[ ] " + super.getDescription() + " from: " + this.from + " to: " + this.to);
        }
    }

    @Override
    public String sendToFile() {
        boolean isDone = true;
        if (this.getStatusIcon().equals(" ")) {
            isDone = false;
        }
        return "E | " + this.getDescription() + " | " + this.from + " | " + this.to + " | " + isDone;
    }
}
