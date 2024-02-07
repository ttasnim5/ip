public class Event extends Task {

    protected String from, to;

    public Event(String description, String from, String to) {
        // tasks that start at a specific date/time and ends at a specific date/time
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String[] userInputSeparatedBySlash) {
        super(userInputSeparatedBySlash[0].replace("event", "").trim());
        this.from = userInputSeparatedBySlash[1].replace("from", "").trim();
        this.to = userInputSeparatedBySlash[2].replace("to", "").trim();
    }

    @Override
    public String toString() {
        if (isDone){
            return ("[E]" + "[" + this.getStatusIcon() + "] " + super.getDescription() + " from:" + this.from + " to: " + this.to);
        }
        else {
            return ("[E]" + "[ ] " + super.getDescription() + " from: " + this.from + " to: " + this.to);
        }
    }
}
