package src.main.java.task;

import src.main.java.taskExceptions.EmptyTaskException;
import src.main.java.taskExceptions.ToDoMismatchedParameterException;

import java.util.Arrays;

public class ToDo extends Task {
    public ToDo(String[] userInputSeparatedBySlash) throws ToDoMismatchedParameterException, EmptyTaskException {
        super(userInputSeparatedBySlash[0].replace("todo", "").trim());

        if (this.getDescription().isEmpty()) {
            throw new EmptyTaskException();
        } else if (userInputSeparatedBySlash.length > 1) {
            throw new ToDoMismatchedParameterException();
        }
    }

    @Override
    public String toString() {
        if (isDone){
            return ("[T]" + "[" + this.getStatusIcon() + "] " + super.getDescription());
        } else {
            return ("[T]" + "[ ] " + super.getDescription());
        }
    }

    @Override
    public String sendToFile() {
        boolean isDone = true;
        if (this.getStatusIcon().equals(" ")) {
            isDone = false;
        }
        return "T | " + this.getDescription() + " | " + isDone;
    }
}
