package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Storage {

    String filePath = "data/duke.txt";
    SimpleDateFormat dateInput = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dateOutput = new SimpleDateFormat("yyyy-MM-dd");

    public void loadFile(MyList myList) {
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String listItem = sc.nextLine();
                String[] splitStringArr = splitString(listItem);
                String task = splitStringArr[0];
                String status = splitStringArr[1];
                String name = splitStringArr[2];

                switch (task) {
                case "T": Todo todo = new Todo(name);
                          todo.setCompleted(status);
                          myList.setListArray(todo);
                          break;
                case "D": String[] deadlineArray = name.split(" \\(by: ");
                          String deadlineDate = deadlineArray[1].substring(0, deadlineArray[1].indexOf(")"));
                          Date parseDeadlineDate = dateInput.parse(deadlineDate);
                          String formatDeadlineDate = dateOutput.format(parseDeadlineDate);
                          Deadline deadline = new Deadline(deadlineArray[0], formatDeadlineDate);
                          deadline.setCompleted(status);
                          myList.setListArray(deadline);
                          break;
                case "E": String[] eventArray = name.split(" \\(at: ");
                          String eventDate = eventArray[1].substring(0, eventArray[1].indexOf(")"));
                          Date parseEventDate = dateInput.parse(eventDate);
                          String formatEventDate = dateOutput.format(parseEventDate);
                          Event event = new Event(eventArray[0], formatEventDate);
                          event.setCompleted(status);
                          myList.setListArray(event);
                          break;
                }
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String string) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(string + "\n");
        fileWriter.close();
    }

    public void newSave(MyList list) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, false);
        for(int i = 1; i <= list.getArraySize(); i++) {
            fileWriter.write(list.getTask(i).toString());
        }
        fileWriter.close();
    }

    private String[] splitString(String string) {
        String[] result = new String[3];
        String[] splitString = string.split("\\]");
        String[] retrieveTask = splitString[0].split("\\[");
        String[] retrieveStatus = splitString[1].split("\\[");
        result[0] = retrieveTask[1];
        result[1] = retrieveStatus[1];
        result[2] = splitString[2].substring(1);
        return result;
    }

}