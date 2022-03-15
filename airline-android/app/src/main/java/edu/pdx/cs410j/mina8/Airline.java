package edu.pdx.cs410j.mina8;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;
import java.util.TreeSet;

public class Airline extends AbstractAirline<Flight> implements Comparable<Airline> {
  private final String name;
  private final Collection<Flight> flights = new TreeSet<>();

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

  @Override
  public int compareTo(Airline o) {
    return this.getName().compareTo(o.getName());
  }
}
