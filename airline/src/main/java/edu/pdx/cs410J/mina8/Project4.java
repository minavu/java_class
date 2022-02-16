package edu.pdx.cs410J.mina8;

import java.util.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project4 {
  public static void main(String[] args) {
    try {
      OptionsHandler optionsHandler = new OptionsHandler();
      ArrayList<String> argsList = optionsHandler.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(args);
      optionsHandler.handleOptionREADME();
      checkThatArgsListIsNotEmpty(argsList);
      String airlineName = argsList.get(0);
      Airline airline = optionsHandler.handleAllBeforeOptions(airlineName);
      Flight newFlight = new Flight(argsList);
      airline.addFlight(newFlight);
      optionsHandler.handleAllAfterOptions(airline, newFlight);
    } catch (IllegalArgumentException | InputMismatchException e) {
      printErrorMessageAndExitSystem(e.getMessage() + USAGE_GUIDE);
    }
    System.exit(0);
  }

  /**
   * This method checks the number of command line arguments that are not options.
   * There should be data for all required fields to create an Airline and a Flight.
   * @param argsList An array list of strings containing data for an Airline and a Flight.
   * @throws IllegalArgumentException If the non-option related arguments from the command line do not add up to the required count for an Airline and a Flight.
   */
  protected static void checkThatArgsListIsNotEmpty(ArrayList<String> argsList) throws IllegalArgumentException {
    if (argsList.size() == 0) {
      throw new IllegalArgumentException("No new flight information was provided through the command line arguments.");
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

  protected final static String DELIMITER = "|";
  protected final static int REQUIRED_ARGS_COUNT = 10;
  protected final static String USAGE_GUIDE =
          " See below for usage guide. Project 4.\n" +
          "usage: java -jar target/airline-2022.0.0.jar [options] <args>\n" +
          "\targs are (in this order):\n" +
          "\t\tairline\t\t\tThe name of the airline\n" +
          "\t\tflightNumber\tThe flight number\n" +
          "\t\tsrc\t\t\t\tThree-letter code of departure airport\n" +
          "\t\tdepart\t\t\tDeparture date and time (24-hour time)\n" +
          "\t\tdest\t\t\tThree-letter code of arrival airport\n" +
          "\t\tarrive\t\t\tArrival date and time (24-hour time)\n" +
          "\toptions are (options may appear in any order):\n" +
          "\t\t-xmlFile file\tWhere to read/write the airline info\n" +
          "\t\t-textFile file\tWhere to read/write the airline info\n" +
          "\t\t-pretty file\tPretty print the airline’s flights to a text file or standard out (file -)\n" +
          "\t\t-print\t\t\tPrints a description of the new flight\n" +
          "\t\t-README\t\t\tPrints a README for this project and exits\n" +
          "\tDate and time should be in the format: mm/dd/yyyy hh:mm am/pm";
}