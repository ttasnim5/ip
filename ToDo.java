public class ToDo extends Task {
    public ToDo(String description) {
        // task without any date/time attached to it
        super(description.replace("todo", "").trim());
    }

    @Override
    public String toString() {
        if (isDone){
            return ("[T]" + "[" + this.getStatusIcon() + "] " + super.getDescription());
        }
        else {
            return ("[T]" + "[ ] " + super.getDescription());
        }
    }
}
