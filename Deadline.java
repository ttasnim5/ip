public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        // tasks that need to be done before a specific date/time
        super(description);
        this.by = by;
    }

    public Deadline(String[] userInputSeparatedBySlash) {
        super(userInputSeparatedBySlash[0].replace("deadline", "").trim());
        this.by = userInputSeparatedBySlash[1].replace("by", "").trim();
    }

   @Override
    public String toString() {
        if (isDone){
            return ("[D]" + "[" + this.getStatusIcon() + "] " + super.getDescription() + " by: " + this.by);
        }
        else {
            return ("[D]" + "[ ] " + super.getDescription() + " by: " + this.by);
        }
    }
}
