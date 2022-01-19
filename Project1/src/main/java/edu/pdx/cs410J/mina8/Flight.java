package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * This is the Flight class for the CS410P/510 airline project.
 * It extends the AbstractFlight class from the instructor's package.
 * Member fields contain information about a flight.
 */
public class Flight extends AbstractFlight {
  private String airline = "";
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
   */
  public Flight(ArrayList<String> argsList) {
    airline = argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase();
    flightNumber = Integer.parseInt(argsList.get(1));
    src = argsList.get(2).toUpperCase();
    depart = argsList.get(3) + " " + argsList.get(4);
    dest = argsList.get(5).toUpperCase();
    arrive = argsList.get(6) + " " + argsList.get(7);
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
