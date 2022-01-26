package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {
  public static void main(String[] args) {
    try {
      Airline airline;
      ArrayList<String> optsList = new ArrayList<>();
      ArrayList<String> argsList = new ArrayList<>();
      createOptionsAndArgumentsListsFromCommandLineArguments(args, optsList, argsList);
      checkOptionsListForCountReadmeAndInvalidOption(optsList);
      checkArgumentsListForCount(argsList);
      String airlineName = argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase();

      if (optsList.contains("-textfile")) {
        int indexOfFileName = optsList.indexOf("-textfile") + 1;
        airline = createAirlineFromTextFileOrNewAirlineIfFileDoesNotExist(optsList.get(indexOfFileName), airlineName);
      } else {
        airline = createAirlineFromArgumentsList(argsList);
      }
      Flight flight = createFlightFromArgumentsList(argsList);
      airline.addFlight(flight);
      if (optsList.contains("-print")) {
        System.out.println(flight);
      }
      if (optsList.contains("-textfile")) {
        int indexOfFileName = optsList.indexOf("-textfile") + 1;
        writeAirlineToTextFile(optsList.get(indexOfFileName), airline);
      }
    } catch (IllegalArgumentException | InputMismatchException e) {
      printErrorMessageAndExitSystem(e.getMessage() + USAGE_GUIDE);
    }
    System.exit(0);
  }

  /**
   * This method writes an Airline to a file.
   * @param filename A string representing a file to write the airline data.
   * @param airline An airline with a name and flights.
   * @throws IllegalArgumentException If the file does not exist.
   */
  protected static void writeAirlineToTextFile(String filename, Airline airline) throws IllegalArgumentException {
    try {
      TextDumper dumper = new TextDumper(filename);
      dumper.dump(airline);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot write Airline to the named text file.");
    }
  }

  /**
   * This method creates an airline from a file or if the file does not exist, will create an empty airline.
   * @param filename A string representing a file to read airline data.
   * @param airlineName A string representing an airline to be used in creating empty airline.
   * @return An airline object.
   * @throws IllegalArgumentException If any arguments to create flight for airline is ill-formatted.
   */
  protected static Airline createAirlineFromTextFileOrNewAirlineIfFileDoesNotExist(String filename, String airlineName) throws IllegalArgumentException {
    try {
      TextParser parser = new TextParser(filename);
      return parser.parse(airlineName);
    } catch (FileNotFoundException | ParserException e) {
      return new Airline(airlineName);
    }
  }

  /**
   * This method checks the number of command line arguments that are not options.
   * There should be data for all required fields to create an Airline and a Flight.
   * @param argsList An array list of strings containing data for an Airline and a Flight.
   * @throws IllegalArgumentException If the non-option related arguments from the command line do not add up to the required count for an Airline and a Flight.
   */
  protected static void checkArgumentsListForCount(ArrayList<String> argsList) throws IllegalArgumentException {
    if (argsList.size() != REQUIRED_ARGS_COUNT) {
      throw new IllegalArgumentException("Incorrect number of command line arguments.");
    }
  }

  /**
   * This method creates an Airline from the parsed command line arguments.
   * @param argsList An array list of strings containing data for an Airline and a Flight.
   * @return An Airline object
   */
  protected static Airline createAirlineFromArgumentsList(ArrayList<String> argsList) {
    Airline airline = new Airline(argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase());
    return airline;
  }

  /**
   * This method creates a Flight from the parsed command line arguments.
   * @param argsList -An array list of strings containing data for an Airline and a Flight.
   * @return -A Flight object.
   * @throws IllegalArgumentException -If the formatting of the parsed arguments does not conform with the required specification.
   */
  protected static Flight createFlightFromArgumentsList(ArrayList<String> argsList) throws IllegalArgumentException {
    Flight flight = new Flight(argsList);
    return flight;
  }

  /**
   * This method checks the options from the user input to ensure there are not too many options called.
   * It also checks for the README option to display the text file and exit the system.
   * @param optsList -An array list of strings containing options for the command line.
   * @throws IllegalArgumentException -If an option listed is not a recognized option by the program or if there are too many options present.
   */
  protected static void checkOptionsListForCountReadmeAndInvalidOption(ArrayList<String> optsList) throws IllegalArgumentException {
    int optsListLength = optsList.size();
    for (int i = 0; i < optsListLength; ++i) {
      if (!ALLOWABLE_OPTIONS.contains(optsList.get(i)) && !optsList.get(i-1).contains("-textfile")) {
        throw new IllegalArgumentException(optsList.get(i) + " is not a valid option.");
      }
      if (optsList.get(i).toLowerCase().contains("-readme")) {
        displayReadmeFileAndExitSystem();
      }
    }
    if (optsListLength > ALLOWABLE_OPTIONS.size()) {
      throw new IllegalArgumentException("There are too many options present.");
    }
  }

  /**
   * This method prints a message to standard error and exits the system with an error code of 1.
   * @param message -A string containing the message to print to standard error.
   */
  private static void printErrorMessageAndExitSystem(String message) {
    System.err.println(message);
    System.exit(1);
  }

  /**
   * This method uses the README.txt resource as an input stream to the buffered reader.
   * It reads every line in the file into a string variable and prints text to stdout.
   * Finally, it exits the system with status code 0, unless the file cannot be found or read.
   */
  private static void displayReadmeFileAndExitSystem() {
    try {
      InputStream readme = Project2.class.getResourceAsStream("README.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = new String();
      while (reader.ready()) {
        line += reader.readLine() + "\n";
      }
      System.out.println(line);
      System.exit(0);
    } catch (NullPointerException e) {
      System.out.println("README.txt not found.");
      System.exit(1);
    } catch (IOException e) {
      System.out.println("README.txt could not be read.");
      System.exit(1);
    }
  }

  /**
   * This method reads a string array of arguments and creates two array lists separating options and arguments.
   * Some options require a parameter.  If present and valid, that parameter will be added to the options list.
   * @param args -The original list of arguments from the command line.
   * @param optsList -An empty array list to store option tags and parameters.
   * @param argsList -An empty array list to store arguments.
   * @throws IllegalArgumentException -If a required parameter for an option is not present.
   */
  protected static void createOptionsAndArgumentsListsFromCommandLineArguments(String[] args, ArrayList<String> optsList, ArrayList<String> argsList) throws IllegalArgumentException {
    for (int i = 0; i < args.length; ++i) {
      if (args[i].startsWith("-")) {
        optsList.add(args[i].toLowerCase());
        if (args[i].contains("-textFile")) {
          if (args[i + 1].startsWith("-")) {
            throw new IllegalArgumentException("A file name was not provided to the -textFile option.");
          }
          try {
            validateTextFileName(args[i + 1]);
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("A file name was not provided to the -textFile option.");
          }
          optsList.add(args[++i].toLowerCase());
        }
      } else {
        argsList.add(args[i].toLowerCase());
      }
    }
  }

  /**
   * This method checks the file name parameter for the textFile option and ensures its validity.
   * @param filename -A string containing the file name.
   * @throws IllegalArgumentException -If the file name is not valid.
   */
  protected static void validateTextFileName(String filename) throws IllegalArgumentException {
    File file = new File(filename + ".temp");
    boolean created = false;
    try {
      created = file.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("The file name for the -textFile option is not valid.");
    } finally {
      if (created) {
        file.delete();
      }
    }
  }


  protected final static String DELIMITER = "|";
  protected final static int REQUIRED_ARGS_COUNT = 8;
  protected final static ArrayList<String> ALLOWABLE_OPTIONS = new ArrayList<>(Arrays.asList("-readme", "-print", "-textfile"));
  protected final static String USAGE_GUIDE =
          " See below for usage guide.\n" +
          "usage: java -jar target/airline-2022.0.0.jar [options] <args>\n" +
          "\targs are (in this order):\n" +
          "\t\tairline\t\t\tThe name of the airline\n" +
          "\t\tflightNumber\tThe flight number\n" +
          "\t\tsrc\t\t\t\tThree-letter code of departure airport\n" +
          "\t\tdepart\t\t\tDeparture date and time (24-hour time)\n" +
          "\t\tdest\t\t\tThree-letter code of arrival airport\n" +
          "\t\tarrive\t\t\tArrival date and time (24-hour time)\n" +
          "\toptions are (options may appear in any order):\n" +
          "\t\t-textFile file\tWhere to read/write the airline info\n" +
          "\t\t-print\t\t\tPrints a description of the new flight\n" +
          "\t\t-README\t\t\tPrints a README for this project and exits\n" +
          "\tDate and time should be in the format: mm/dd/yyyy hh:mm";
}