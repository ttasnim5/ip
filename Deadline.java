import java.util.Arrays;

public class Deadline extends Task {

    protected String by = "";

    public Deadline(String[] userInputSeparatedBySlash) throws DeadlineMismatchedParameterException, EmptyTaskException {
        super(userInputSeparatedBySlash[0].replace("deadline", "").trim());

        System.out.println(Arrays.toString(userInputSeparatedBySlash));
        System.out.println("description = " + this.getDescription());

        if(this.getDescription().isEmpty()) {
            throw new EmptyTaskException();
        }
        else if (userInputSeparatedBySlash.length > 2) {
            // there's multiple parameters, /by or otherwise
            throw new DeadlineMismatchedParameterException();
        }
        else if (!userInputSeparatedBySlash[1].contains("by")) {
            // if the singular parameter is not by
            throw new DeadlineMismatchedParameterException();
        }

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
