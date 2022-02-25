package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;
import java.util.TreeSet;

/**
 * This is the Airline class for the CS410P/510 airline project.
 * It extends the AbstractAirline class from the instructor's package.
 * Member fields contain information about an airline such as name and the flights.
 */
public class Airline extends AbstractAirline<Flight> implements Comparable<Airline> {
  private final String name;
  private final Collection<Flight> flights = new TreeSet<>();

  /**
   * This is the argument constructor for the class.
   * @param name A string to represent the name of the airline.
   */
  public Airline(String name) {
    this.name = name;
  }

  /**
   * This is the accessor method to get the name of the airline.
   * @return A string representing the name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * This is a mutator method to add a flight to the airline.
   * @param flight A Flight to be added to the airline.
   */
  @Override
  public void addFlight(Flight flight) {
    flights.add(flight);
  }

  /**
   * This is the accessor method to get the flights in the airline.
   * @return A collection of Flights.
   */
  @Override
  public Collection<Flight> getFlights() {
    return flights;
  }

  /**
   * This method allows two airlines to be compared by their names.
   * @param o The other airline to compare.
   * @return -1, 0, 1 to indicate comparison of the two airlines.
   */
  @Override
  public int compareTo(Airline o) {
    return this.getName().compareTo(o.getName());
  }
}
