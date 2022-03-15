package edu.pdx.cs410j.mina8;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.*;

public class TextDumper implements AirlineDumper<Airline> {
  private static final String DELIMITER = "|";
  private final Writer writer;

  public TextDumper(Writer writer) throws IOException {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {
    try ( PrintWriter pw = new PrintWriter(this.writer) ) {
      Collection<Flight> flights = airline.getFlights();
      flights.forEach((flight) ->
              pw.println(airline.getName() + DELIMITER +
                      flight.getNumber() + DELIMITER +
                      flight.getSource() + DELIMITER +
                      flight.getDepartureStringForFile().replace(" ", DELIMITER) + DELIMITER +
                      flight.getDestination() + DELIMITER +
                      flight.getArrivalStringForFile().replace(" ", DELIMITER))
              );
      if (flights.isEmpty()) {
        pw.println(airline.getName());
      }

      pw.flush();
    }
  }
}
