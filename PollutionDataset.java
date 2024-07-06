import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

public class PollutionDataset {

  private LinkedList<Measurement> dataset;

  public PollutionDataset() {
    dataset = new LinkedList<>();
  }

  public void readCSV(String filename) throws FileNotFoundException {
    Scanner reader = new Scanner(new File(filename));
    int i = 0;
    dataset.clear();
    reader.nextLine();

    // reads lines from the CSV file, creates Measurements using them and adds each Measurement to dataset
    while (reader.hasNextLine()){
      String line = reader.nextLine();
      Measurement record = new Measurement(line);

      dataset.add(record);
    }

    reader.close();
  }

  public void add(Measurement m) {
    dataset.add(m);
  }

  public int size() {
    return dataset.size();
  }

  public Measurement get(int index) {
    // checks if dataset is empty and throws an exception if it is
    if (dataset.size() == 0){

      throw new DataException("List is empty.");
    }
    else{

      return dataset.get(index);
    }
  }

  public Measurement maxLevel() {
    // checks if dataset is empty and throws an exception if it is
    if (dataset.size() == 0){

      throw new DataException("List is empty.");
    }
    else{

      int highest = 0;
      int index = 0;

      // finds the Measurement in dataset with the highest value for level
      for (int i = 0; i < dataset.size(); i++){

        Measurement record = dataset.get(i);

        if (record.getLevel() > highest){

          highest = record.getLevel();
          index = i;
        }
      }

      return dataset.get(index);
    }
  }

  public Measurement minLevel() {
    // checks if dataset is empty and throws an exception if it is
    if (dataset.size() == 0){

      throw new DataException("List is empty.");
    }
    else{

      int lowest = 0;

      // finds the first Measurement in dataset that does not have a value for level and sets the value of lowest to this
      for (int i = 0; i < dataset.size(); i++){

        if (dataset.get(i).getLevel() != -1){

          lowest = dataset.get(i).getLevel();
          break;
        }
      }

      int index = 0;

      // finds the Measurement in dataset that has the lowest value for level that is not -1
      for (int i = 0; i < dataset.size(); i++){

        Measurement record = dataset.get(i);

        if (record.getLevel() < lowest && record.getLevel() != -1){

          lowest = record.getLevel();
          index = i;
        }
      }

      return dataset.get(index);
    }
  }

  public double meanLevel() {
    // checks if dataset is empty and throws an exception if it is
    if (dataset.size() == 0){

      throw new DataException("List is empty.");
    }
    else{

      double totalLevel = 0;
      double totalRecords = 0;

      // adds up all the values for level for every Measurement in dataset not including Measurements where level == -1
      for (int i = 0; i < dataset.size(); i++){
        if (dataset.get(i).getLevel() != -1){
          totalLevel += dataset.get(i).getLevel();
          totalRecords += 1;
        }
      }

      return totalLevel/totalRecords;
    }
  }

  public LocalDate dayRulesBreached() {
    // checks if dataset is empty and throws an exception if it is
    if (dataset.size() == 0){

      throw new DataException("List is empty.");
    }
    else{

      int broken = 0;
      LocalDate day = null;

      // checks every Measurement in dataset
      for (int i = 0; i < dataset.size(); i++){

        int hourTotal = 0;

        // calculates the hourly total level not including values of -1
        for (int k = i; k < i+4; k++){

          if  (dataset.get(k).getLevel() != -1)
            hourTotal += dataset.get(k).getLevel();
        }

        // checks to see if the hourly total exceeds 200 and gets the day that this occurs whilst also increasing the number of times EU rules have been breached
        if (hourTotal > 200){

          broken += 1;
          day = dataset.get(i).getTime().toLocalDate();
        }

        i += 3;

        // checks if the number of EU rules breaches exceeds 18 and returns the 19th day that EU rules have been breached
        if (broken > 18){

          return day;
        }
      }

      return null;
    }
  }
}
