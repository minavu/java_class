package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * This is the Flight class for the CS410P/510 airline project.
 * It extends the AbstractFlight class from the instructor's package.
 * Member fields contain information about a flight.
 */
public class Flight extends AbstractFlight {
  private int flightNumber = 0;
  private String src = "";
  private String depart = "";
  private String dest = "";
  private String arrive = "";

  /**
   * This is the default constructor for the Flight class and does not take any arguments.
   */
  public Flight() {}

  /**
   * This is the argument constructor for the Flight class and requires an array list of string arguments.
   * @param argsList  An array list of strings containing arguments from the command line.
   * @throws IllegalArgumentException
   */
  public Flight(ArrayList<String> argsList) throws IllegalArgumentException {
    try {
      checkArgsForValidity(argsList);
    } catch (IllegalArgumentException e) {
      throw e;
    }
    flightNumber = Integer.parseInt(argsList.get(1));
    src = argsList.get(2).toUpperCase();
    depart = argsList.get(3) + " " + argsList.get(4);
    dest = argsList.get(5).toUpperCase();
    arrive = argsList.get(6) + " " + argsList.get(7);
  }

  /**
   * This method checks the validity of the arguments provided by the user of the program.
   * The usage specification requires the second argument to be a number,
   * the third and sixth arguments to be a three-letter code,
   * and the fourth/fifth and seventh/eighth arguments form valid date/time format.
   * @param argsList  A list of arguments without any option tags.
   * @return          A boolean indicating the validity of all arguments.
   * @throws IllegalArgumentException
   */
  private static void checkArgsForValidity(ArrayList<String> argsList) throws IllegalArgumentException {
    try {
      Integer.parseInt(argsList.get(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The given flight number is not a number.");
    }
    if (argsList.get(2).length() != 3) {
      throw new IllegalArgumentException("The source code must be three letters.");
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The source code must consist of only letters.");
        }
      }
    }
    if (!validDateTime(argsList.get(3) + " " + argsList.get(4))) {
      throw new IllegalArgumentException("The departure date and time is not in an acceptable format.");
    }
    if (argsList.get(5).length() != 3) {
      throw new IllegalArgumentException("The destination code must be three letters.");
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The destination code must consist of only letters.");
        }
      }
    }
    if (!validDateTime(argsList.get(6) + " " + argsList.get(7))) {
      throw new IllegalArgumentException("The arrival date and time is not in an acceptable format.");
    }
  }

  /**
   * This method checks a string against a regular expression that conforms to the required date/time format.
   * @param dateTime  A string to check date/time format.
   * @return          A boolean indicating the validity of the string.
   */
  private static boolean validDateTime(String dateTime) {
    String regex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4} (0[0-9]|1[0-9]|2[0-3]|[1-9]):([0-5][0-9])$";
    return dateTime.matches(regex);
  }

  /**
   * This is the accessor method to get the flight number.
   * @return          An integer that represents the flight number.
   */
  @Override
  public int getNumber() {
    return flightNumber;
  }

  /**
   * This is the accessor method to get the source location of the flight.
   * @return          A string representing the source location.
   */
  @Override
  public String getSource() {
    return src;
  }

  /**
   * This is the accessor method to get the departure date and time of the flight.
   * @return          A string representing the departure date and time.
   */
  @Override
  public String getDepartureString() {
    return depart;
  }

  /**
   * This is the accessor method to get the destination of the flight.
   * @return          A string representing the destination.
   */
  @Override
  public String getDestination() {
    return dest;
  }

  /**
   * This is the accessor method to get the arrival date and time of a flight.
   * @return          A string representing the arrival date and time.
   */
  @Override
  public String getArrivalString() {
    return arrive;
  }
}
