package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> flights = new ArrayList<Flight>();

  public Airline(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }
}
