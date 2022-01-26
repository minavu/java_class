package edu.pdx.cs410J.mina8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {
  public static void main(String[] args) {
    try {
      ArrayList<String> optsList = new ArrayList<>();
      ArrayList<String> argsList = new ArrayList<>();
      createOptionsAndArgumentsListsFromCommandLineArguments(args, optsList, argsList);
      checkOptionsListForCountReadmeAndInvalidOption(optsList);
      checkArgumentsListForCount(argsList);
      Airline airline = createAirlineFromArgumentsList(argsList);
      Flight flight = createFlightFromArgumentsList(argsList);
      airline.addFlight(flight);
      if (optsList.contains("-print")) {
        System.out.println(flight);
      }
    } catch (IllegalArgumentException e) {
      printErrorMessageAndExitSystem(e.getMessage() + USAGE_GUIDE);
    }
    System.exit(0);
  }

  protected static void checkArgumentsListForCount(ArrayList<String> argsList) throws IllegalArgumentException {
    if (argsList.size() != REQUIRED_ARGS_COUNT) {
      throw new IllegalArgumentException("Incorrect number of command line arguments.");
    }
  }

  protected static Airline createAirlineFromArgumentsList(ArrayList<String> argsList) {
    Airline airline = new Airline(argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase());
    return airline;
  }

  protected static Flight createFlightFromArgumentsList(ArrayList<String> argsList) throws IllegalArgumentException {
    Flight flight = new Flight(argsList);
    return flight;
  }

  protected static void checkOptionsListForCountReadmeAndInvalidOption(ArrayList<String> optsList) throws IllegalArgumentException {
    if (optsList.size() > ALLOWABLE_OPTIONS.size()) {
      throw new IllegalArgumentException("There are too many options present.");
    }
    for (String opt : optsList) {
      if (!ALLOWABLE_OPTIONS.contains(opt)) {
        throw new IllegalArgumentException(opt + " is not a valid option.");
      }
      if (opt.toLowerCase().contains("-readme")) {
        displayReadmeFileAndExitSystem();
      }
    }
  }

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
   * It first checks for the -readme option in the arguments list.
   * If present, it calls the displayReadmeFile() method to execute.
   * It also checks the number of options and arguments to make a return.
   * @param args      The original list of arguments from the command line.
   * @param optsList  An empty list to store option tags.
   * @param argsList  An empty list to store arguments.
   */
  protected static void createOptionsAndArgumentsListsFromCommandLineArguments(String[] args, ArrayList<String> optsList, ArrayList<String> argsList) {
    for (String arg : args) {
      if (arg.startsWith("-")) {
        optsList.add(arg.toLowerCase());
      } else {
        argsList.add(arg.toLowerCase());
      }
    }
  }


  protected final static String DELIMITER = "|";
  protected final static int REQUIRED_ARGS_COUNT = 8;
  protected final static ArrayList<String> ALLOWABLE_OPTIONS = new ArrayList<>(Arrays.asList("-readme", "-print", "-textFile"));
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
          "\t\t-print\t\t\tPrints a description of the new flight\n" +
          "\t\t-README\t\t\tPrints a README for this project and exits\n" +
          "\tDate and time should be in the format: mm/dd/yyyy hh:mm";
}