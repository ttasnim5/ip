package src.main.java.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "¤" : " "); // mark done task with X
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String toString() {
        if (isDone){
            return ("[T]" + "[" + this.getStatusIcon() + "] " + this.description);
        } else {
            return ("[T]" + "[ ] " + this.description);
        }
    }

    public String sendToFile() {
        return description + " | " + isDone;
    }
}
