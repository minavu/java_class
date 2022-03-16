package edu.pdx.cs410j.mina8;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Flight extends AbstractFlight implements Comparable<Flight> {
  private String airline = "";
  private int flightNumber = 0;
  private String src = "";
  private Date depart = null;
  private String dest = "";
  private Date arrive = null;
  private static final DateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
  private static final DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

  public Flight() {}

  public Flight(ArrayList<String> argsList) throws IllegalArgumentException {
    try {
      checkArgsForValidityAndAddDataToCorrectFields(argsList);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  private void checkArgsForValidityAndAddDataToCorrectFields(ArrayList<String> argsList) throws IllegalArgumentException {
    if (!argsList.get(0).isEmpty()) {
      airline = argsList.get(0);
    } else {
      throw new IllegalArgumentException("The airline name cannot be empty. Given input is " + argsList.get(0));
    }

    try {
      flightNumber = Integer.parseInt(argsList.get(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The given flight number is not a number. Given input is " + argsList.get(1));
    }

    if (argsList.get(2).length() != 3) {
      throw new IllegalArgumentException("The source code must be three letters. Given input is " + argsList.get(2));
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The source code must consist of only letters. Given input is " + argsList.get(2));
        }
      }
      src = argsList.get(2).toUpperCase();
      if (!AirportNames.getNamesMap().containsKey(src)) {
        throw new IllegalArgumentException("The source code does not correspond to a known airport. Given input is " + argsList.get(2));
      }
    }

    try {
      if (!validDate(argsList.get(3))) {
        throw new IllegalArgumentException("The departure date is not in the correct format. Given date is " + argsList.get(3));
      }
      depart = dateParser.parse(argsList.get(3) + " " + argsList.get(4) + " " + argsList.get(5));
    } catch (ParseException | IllegalArgumentException e) {
      throw new IllegalArgumentException("The departure date and time is not in an acceptable format. Given date and time is " + argsList);
    }

    if (argsList.get(6).length() != 3) {
      throw new IllegalArgumentException("The destination code must be three letters. Given input is " + argsList.get(6));
    } else {
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(argsList.get(2).charAt(i))) {
          throw new IllegalArgumentException("The destination code must consist of only letters. Given input is " + argsList.get(6));
        }
      }
      dest = argsList.get(6).toUpperCase();
      if (!AirportNames.getNamesMap().containsKey(dest)) {
        throw new IllegalArgumentException("The destination code does not correspond to a known airport. Given input is " + argsList.get(6));
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

  private static boolean validDate(String date) {
    String regex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4}$";
    return date.matches(regex);
  }

  public String getAirline() {
    return airline;
  }

  @Override
  public int getNumber() {
    return flightNumber;
  }

  @Override
  public String getSource() {
    return src;
  }

  @Override
  public Date getDeparture() {
    return depart;
  }

  @Override
  public String getDepartureString() {
    return dateFormatter.format(depart).replace(",", "");
  }

  public String getDepartureStringForFile() {
    return dateParser.format(depart);
  }

  @Override
  public String getDestination() {
    return dest;
  }

  @Override
  public Date getArrival() {
    return arrive;
  }

  @Override
  public String getArrivalString() {
    return dateFormatter.format(arrive).replace(",", "");
  }

  public String getArrivalStringForFile() {
    return dateParser.format(arrive);
  }

  @Override
  public int compareTo(Flight other) {
    if (this.getSource().compareTo(other.getSource()) == 0) {
      return this.getDeparture().compareTo(other.getDeparture());
    }
    return this.getSource().compareTo(other.getSource());
  }

  public String getFlightDuration() {
    long timeDifferenceInMilliseconds = arrive.getTime() - depart.getTime();
    return TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMilliseconds) + " min";
  }
}
