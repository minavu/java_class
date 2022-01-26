package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public TextDumper(String fileName) throws IOException {
    this.writer = new FileWriter(fileName);
  }

  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {
      ArrayList<Flight> flights = (ArrayList<Flight>) airline.getFlights();
      flights.forEach((flight) ->
              pw.println(airline.getName() + Project2.DELIMITER +
                      flight.getNumber() + Project2.DELIMITER +
                      flight.getSource() + Project2.DELIMITER +
                      flight.getDepartureString().split(" ")[0] + Project2.DELIMITER +
                      flight.getDepartureString().split(" ")[1] + Project2.DELIMITER +
                      flight.getDestination() + Project2.DELIMITER +
                      flight.getArrivalString().split(" ")[0] + Project2.DELIMITER +
                      flight.getArrivalString().split(" ")[1])
              );

      pw.flush();
    }
  }
}
