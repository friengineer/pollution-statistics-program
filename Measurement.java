import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Measurement {
  private static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  private LocalDateTime time;
  private int level;

  public Measurement(String record) {
    // splits CSV string
    String[] separatedRecord = record.split(",");

    // throws an exception if there are too many or too few elements in the CSV string
    if (separatedRecord.length < 2 || separatedRecord.length > 3)
      throw new DataException("Invalid input");

    String dateTime = separatedRecord[0] + " " + separatedRecord[1];
    time = LocalDateTime.parse(dateTime, FORMAT);

    // checks the number of elements in th CSV string and adjusts the value for level accordingly
    if (separatedRecord.length == 3){

      level = Integer.parseInt(separatedRecord[2]);
    }
    else{

      level = -1;
    }
  }

  public LocalDateTime getTime() {
    return time;
  }

  public int getLevel() {
    return level;
  }

  public String toString() {
    String string = time + ", ";

    // checks if there is a value for level and adjusts the output string accordingly
    if (level == -1){

      string += "no data";
    }
    else{

      string += level + " \u00b5g/m\u00b3";
    }

    return string;
  }
}
