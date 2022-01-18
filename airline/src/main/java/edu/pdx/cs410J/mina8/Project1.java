package edu.pdx.cs410J.mina8;

import java.util.ArrayList;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  public static void main(String[] args) {
    ArrayList<String> optsList = new ArrayList<>();
    ArrayList<String> argsList = new ArrayList<>();
    if (!checkArgsCountAndCreateOptsAndArgsLists(args, optsList, argsList) || !checkArgsForValidity(argsList)) {
      System.err.println("Incorrect command line arguments. See below for usage guide.\n" + USAGE_GUIDE);
      System.exit(1);
    }
    Airline airline = new Airline(argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase());
    airline.addFlight(new Flight(argsList));
    System.out.println(airline);
    System.exit(0);
  }

  private static boolean checkArgsCountAndCreateOptsAndArgsLists(String[] args, ArrayList<String> optsList, ArrayList<String> argsList) {
    for (String arg : args) {
      if (arg.startsWith("-")) {
        optsList.add(arg);
      } else {
        argsList.add(arg);
      }
    }
    if (optsList.size() > OPTIONAL_OPTS_COUNT || argsList.size() != REQUIRED_ARGS_COUNT) {
      return false;
    }
    return true;
  }

  private static boolean checkArgsForValidity(ArrayList<String> argsList) {
    try {
      Integer.parseInt(argsList.get(1));
      if (argsList.get(2).length() != 3 || argsList.get(5).length() != 3) {
        throw new IllegalArgumentException();
      }
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i)) || !Character.isLetter(argsList.get(5).charAt(i))) {
          throw new IllegalArgumentException();
        }
      }
      if (!validDateTime(argsList.get(3) + " " + argsList.get(4)) || !validDateTime(argsList.get(6) + " " + argsList.get(7))) {
        throw new IllegalArgumentException("depart or arrive is not in correct date/time format");
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  private static boolean validDateTime(String dateTime) {
    String regex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4} (0[0-9]|1[0-9]|2[0-3]|[1-9]):([0-5][0-9])$";
    return dateTime.matches(regex);
  }

  protected final static int REQUIRED_ARGS_COUNT = 8;
  protected final static int OPTIONAL_OPTS_COUNT = 2;
  protected final static String USAGE_GUIDE =
          "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
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