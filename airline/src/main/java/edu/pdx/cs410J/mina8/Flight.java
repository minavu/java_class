package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This is the Flight class for the CS410P/510 airline project.
 * It extends the AbstractFlight class from the instructor's package.
 * Member fields contain information about a flight.
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
  private int flightNumber = 0;
  private String src = "";
  private Date depart = null;
  private String dest = "";
  private Date arrive = null;
  private static final DateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
  private static final DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

  /**
   * This is the default constructor for the Flight class and does not take any arguments.
   * This is necessary for the generic type of classes to work.
   */
  public Flight() {}

  /**
   * This is a constructor that takes an array list of strings containing the arguments to create a Flight.
   * It checks each argument for validity before creating the Flight object.
   * @param argsList -An array list of strings containing arguments from the command line.
   * @throws IllegalArgumentException -If any of the arguments does not match the required specification.
   */
  public Flight(ArrayList<String> argsList) throws IllegalArgumentException {
    try {
      checkArgsForValidityAndAddDataToCorrectFields(argsList);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  /**
   * This method checks the validity of the arguments provided by the user of the program.
   * The usage specification requires the second argument to be a number,
   * the third and sixth arguments to be a three-letter code,
   * and the fourth/fifth and seventh/eighth arguments form valid date/time format.
   * @param argsList -A list of arguments without any option tags.
   * @throws IllegalArgumentException -If any of the arguments does not conform to the required format.
   */
  private void checkArgsForValidityAndAddDataToCorrectFields(ArrayList<String> argsList) throws IllegalArgumentException {
    if (argsList.size() != Project4.REQUIRED_ARGS_COUNT) {
      throw new IllegalArgumentException("The number of arguments provided is not correct. Given count is " + argsList.size() + ": " + argsList.toString());
    }

    try {
      flightNumber = Integer.parseInt(argsList.get(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The given flight number is not a number. Given arg is " + argsList.get(1));
    }

    if (argsList.get(2).length() != 3) {
      throw new IllegalArgumentException("The source code must be three letters. Given arg is " + argsList.get(2));
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The source code must consist of only letters. Given arg is " + argsList.get(2));
        }
      }
      src = argsList.get(2).toUpperCase();
      if (!AirportNames.getNamesMap().containsKey(src)) {
        throw new IllegalArgumentException("The source code does not correspond to a known airport. Given arg is " + argsList.get(2));
      }
    }

    try {
      if (!validDate(argsList.get(3))) {
        throw new IllegalArgumentException("The departure date is not in the correct format. Given date is " + argsList.get(3));
      }
      depart = dateParser.parse(argsList.get(3) + " " + argsList.get(4) + " " + argsList.get(5));
    } catch (ParseException | IllegalArgumentException e) {
      throw new IllegalArgumentException("The departure date and time is not in an acceptable format. Given date and time is " + depart);
    }

    if (argsList.get(6).length() != 3) {
      throw new IllegalArgumentException("The destination code must be three letters. Given arg is " + argsList.get(6));
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The destination code must consist of only letters. Given arg is " + argsList.get(6));
        }
      }
      dest = argsList.get(6).toUpperCase();
      if (!AirportNames.getNamesMap().containsKey(dest)) {
        throw new IllegalArgumentException("The destination code does not correspond to a known airport. Given arg is " + argsList.get(6));
      }
    }

    try {
      if (!validDate(argsList.get(7))) {
        throw new IllegalArgumentException("The arrival date is not in the correct format. Given date is " + argsList.get(7));
      }
      arrive = dateParser.parse(argsList.get(7) + " " + argsList.get(8) + " " + argsList.get(9));
    } catch (ParseException | IllegalArgumentException e) {
      throw new IllegalArgumentException("The arrival date and time is not in an acceptable format. Given date and time is " + arrive);
    }
    if (arrive.before(depart)) {
      throw new IllegalArgumentException("The arrival date and time cannot be before the departure date and time.");
    }
  }

  /**
   * This method checks a string against a regular expression that conforms to the required date/time format.
   * @param date -A string to check date/time format.
   * @return -A boolean indicating the validity of the string.
   */
  private static boolean validDate(String date) {
    String regex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4}$";
    return date.matches(regex);
  }

  /**
   * This is the accessor method to get the flight number.
   * @return -An integer that represents the flight number.
   */
  @Override
  public int getNumber() {
    return flightNumber;
  }

  /**
   * This is the accessor method to get the source location of the flight.
   * @return -A string representing the source location.
   */
  @Override
  public String getSource() {
    return src;
  }

  /**
   * This method returns the date of departure.
   * @return A date object of departure.
   */
  @Override
  public Date getDeparture() {
    return depart;
  }

  /**
   * This is the accessor method to get the departure date and time of the flight.
   * @return -A string representing the departure date and time.
   */
  @Override
  public String getDepartureString() {
    return dateFormatter.format(depart).replace(",", "");
  }

  /**
   * This is method to return the departure date in the correct format to store in file.
   * @return A string to store in file.
   */
  public String getDepartureStringForFile() {
    return dateParser.format(depart);
  }

  /**
   * This is the accessor method to get the destination of the flight.
   * @return -A string representing the destination.
   */
  @Override
  public String getDestination() {
    return dest;
  }

  /**
   * This is the mehtod to get the arrival date.
   * @return A date object.
   */
  @Override
  public Date getArrival() {
    return arrive;
  }

  /**
   * This is the accessor method to get the arrival date and time of a flight.
   * @return -A string representing the arrival date and time.
   */
  @Override
  public String getArrivalString() {
    return dateFormatter.format(arrive).replace(",", "");
  }

  /**
   * This method returns a string of the arrival date in correct format to store.
   * @return A string representation.
   */
  public String getArrivalStringForFile() {
    return dateParser.format(arrive);
  }

  /**
   * This method overrides the Comparable method so that each flight can be ordered naturally by source and departure date/time.
   * @param other The other flight to compare to.
   * @return An integer indicating less than, greater than, or equal to of the two flights.
   */
  @Override
  public int compareTo(Flight other) {
    if (this.getSource().compareTo(other.getSource()) == 0) {
      return this.getDeparture().compareTo(other.getDeparture());
    }
    return this.getSource().compareTo(other.getSource());
  }

  /**
   * This method returns the duration of the flight in minutes only.
   * @return A string representing the duration of the flight.
   */
  public String getFlightDuration() {
    long timeDifferenceInMilliseconds = arrive.getTime() - depart.getTime();
    return TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMilliseconds) + " min";
  }
}
