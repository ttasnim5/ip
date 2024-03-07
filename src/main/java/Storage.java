package src.main.java;

import src.main.java.task.Task;

import java.io.*;

public class Storage {
    private static File outfile = new File("taskDrive.txt");
    private static FileWriter overwriter, appender;

    public static void closeFile() {
        try {
            FileWriter appender = new FileWriter(outfile, true);
            appender.write("xx\n");
            appender.flush();
            appender.close();
        } catch (IOException e) {
            System.out.println("could not end the file properly");
        }
    }

    public static void updateFile(int mode) throws IOException {
        Task[] tasklist = TaskList.getTaskList();
        int taskcount = TaskList.getTaskCount();
        if (mode == 1) { // just adding to the list
            FileWriter appender = new FileWriter(outfile, true);
            appender.write(tasklist[taskcount - 1].sendToFile() + "\n");
            appender.flush();
            appender.close();
        } else { // in the case of deleting a task, marking a task as done/undone
            FileWriter overwriter = new FileWriter(outfile, false);
            for (int i = 0; i < taskcount; i++) {
                overwriter.write(tasklist[i].sendToFile() + "\n");
                overwriter.flush();
            }
            overwriter.close();
        }
    }

    public static void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("taskDrive.txt"));
            String line = reader.readLine();
            int count = 0, doneIndex = 0;

            while (line != null && !line.equals("xx")) {
                Task[] tasklist = TaskList.getTaskList();
                int taskcount = TaskList.getTaskCount();
                String[] pastData = line.split("\\|");

                String tasktype = pastData[0].trim();
                if (tasktype.equals("T")) {
                    TaskList.addToList("todo " + pastData[1]);
                    doneIndex = 2;
                } else if (tasktype.equals("D")) {
                    TaskList.addToList("deadline " + pastData[1] + " /by " + pastData[2]);
                    doneIndex = 3;
                } else if (tasktype.equals("E")) {
                    TaskList.addToList("event " + pastData[1] + " /from " + pastData[2] + " /to " + pastData[3]);
                    doneIndex = 4;
                }

                if (pastData[doneIndex].trim().equals("true")) {
                    String[] commandWords = new String[2];
                    commandWords[0] = "mark";
                    commandWords[1] = Integer.toString(count);
                    TaskList.changeTaskMarker(commandWords);
                }

                count++;
                line = reader.readLine();
            }
            reader.close();
            // clear out the file to get rid of the old data and prevent repeats
            overwriter = new FileWriter(outfile, false);
            overwriter.write("");
            overwriter.flush();
            overwriter.close();

            // rewrite the present list into taskDrive.txt
            updateFile(0);
        } catch (FileNotFoundException e) {
            System.out.println("\tfile not found\t");
        } catch (IOException e) {
            System.out.println("\tio exception\t");
        }
    }
}
