import java.io.FileNotFoundException;

public class Pollution{
  public static void main(String[] args){
    // checks if a filename has been specified and if not outputs a helpful message
    if (args.length == 0){

      System.out.println("Please specify a valid file name or file path after entering 'java Pollution'.");
      System.out.println("(e.g. 'java Pollution ../data/kirkstall.csv')");
    }
    else{

      // checks if any errors are thrown and deals with them accordingly if so
      try{

        PollutionDataset file = new PollutionDataset();
        file.readCSV(args[0]);

        System.out.printf("Number of records in file: %d\n", file.size());
        System.out.printf("Highest NO\u2082 level: %s\n", file.maxLevel().toString());
        System.out.printf("Lowest NO\u2082 level: %s\n", file.minLevel().toString());
        System.out.printf("Mean NO\u2082 level: %f \u00b5g/m\u00b3\n", file.meanLevel());

        // checks if the EU rules have been breached and adjusts the output accordingly
        if (file.dayRulesBreached() != null){

          System.out.printf("EU rules were breached on the following date: ");
          System.out.println(file.dayRulesBreached());
        }
        else{

          System.out.println("EU rules were not breached.");
        }
      }
      catch (FileNotFoundException e){

        System.err.println("FileNotFoundException: File does not exist.");
      }
      catch (DataException e){

        System.err.println("DataException: " + e.getMessage());
      }
    }
  }
}
