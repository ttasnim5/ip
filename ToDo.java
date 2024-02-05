public class ToDo extends Task {
    protected String by;

    public ToDo(String description, String by) {
        // task without any date/time attached to it
        super(description);
    }

    public String toString(Boolean includeIcon) {
        if (includeIcon){
            return ("[T]" + "[" + this.getStatusIcon() + "] " + super.toString());
        }
        else {
            return ("[T]" + "[ ] " + super.toString());
        }
    }
}
