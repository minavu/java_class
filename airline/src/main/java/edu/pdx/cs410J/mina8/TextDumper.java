package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.*;

/**
 * This class writes an Airline to a text file along with all of its associated flights.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  /**
   * This is a constructor that takes a Writer object.
   * @param writer -A Writer object.
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * This is a constructor that takes a String representing the file name of where to write data.
   * @param fileName -A string representing the file path and file name.
   * @throws IOException -If the named file is not a regular file, cannot be created, or cannot be opened.
   */
  public TextDumper(String fileName) throws IOException {
    this.writer = new FileWriter(fileName);
  }

  /**
   * This method writes the Airline data to a text file along with all associated flights.
   * A delimiter is used to separate fields and is taken from the Project4 class.
   * @param airline -An airline object with a name and flights.
   */
  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {
      Collection<Flight> flights = airline.getFlights();
      flights.forEach((flight) ->
              pw.println(airline.getName() + Project4.DELIMITER +
                      flight.getNumber() + Project4.DELIMITER +
                      flight.getSource() + Project4.DELIMITER +
                      flight.getDepartureStringForFile().replace(" ", Project4.DELIMITER) + Project4.DELIMITER +
                      flight.getDestination() + Project4.DELIMITER +
                      flight.getArrivalStringForFile().replace(" ", Project4.DELIMITER))
              );
      if (flights.isEmpty()) {
        pw.println(airline.getName());
      }

      pw.flush();
    }
  }
}
