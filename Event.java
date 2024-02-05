public class Event extends Task {

    protected String from, to;

    public Event(String description, String from, String to) {
        // tasks that start at a specific date/time and ends at a specific date/time
        super(description);
        this.from = from;
        this.to = to;
    }

    public String toString(Boolean includeIcon) {
        if (includeIcon){
            return ("[E]" + "[" + this.getStatusIcon() + "] " + super.toString() + "from: " + this.from + "to: " + this.to);
        }
        else {
            return ("[E]" + "[ ] " + super.toString() + "from: " + this.from + "to: " + this.to);
        }
    }
}
