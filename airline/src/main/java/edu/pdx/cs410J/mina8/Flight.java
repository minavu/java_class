package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

public class Flight extends AbstractFlight {
  private String airline = "";
  private int flightNumber = 0;
  private String src = "";
  private String depart = "";
  private String dest = "";
  private String arrive = "";

  public Flight() {}

  public Flight(ArrayList<String> argsList) {
    airline = argsList.get(0).substring(0,1).toUpperCase() + argsList.get(0).substring(1).toLowerCase();
    flightNumber = Integer.parseInt(argsList.get(1));
    src = argsList.get(2).toUpperCase();
    depart = argsList.get(3) + " " + argsList.get(4);
    dest = argsList.get(5).toUpperCase();
    arrive = argsList.get(6) + " " + argsList.get(7);
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
  public String getDepartureString() {
    return depart;
  }

  @Override
  public String getDestination() {
    return dest;
  }

  @Override
  public String getArrivalString() {
    return arrive;
  }
}
