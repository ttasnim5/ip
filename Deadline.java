public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        // tasks that need to be done before a specific date/time
        super(description);
        this.by = by;
    }

    public String toString(Boolean includeIcon) {
        if (includeIcon){
            return ("[D]" + "[" + this.getStatusIcon() + "] " + super.toString() + "by: " + this.by);
        }
        else {
            return ("[D]" + "[ ] " + super.toString() + "by: " + this.by);
        }
    }
}
