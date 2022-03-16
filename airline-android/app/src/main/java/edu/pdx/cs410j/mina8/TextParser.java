package edu.pdx.cs410j.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException, IllegalArgumentException {
    return null;
  }

  public TreeSet<Airline> parseHub() throws ParserException, IllegalArgumentException {
    TreeSet<Airline> hub = new TreeSet<>();
    try ( BufferedReader br = new BufferedReader(this.reader) ) {

      Airline airline = null;
      while (br.ready()) {
        String[] parsedData = br.readLine().split("\\|");
        if (airline == null) {
          airline = new Airline(parsedData[0]);
        }
        Flight flight = new Flight(new ArrayList<>(Arrays.asList(parsedData)));

        if (!airline.getName().equals(flight.getAirline())) {
          hub.add(airline);
          airline = new Airline(flight.getAirline());
        }
        airline.addFlight((flight));
      }
      if (airline != null) {
        hub.add(airline);
      }
    } catch (IOException e) {
      throw new ParserException("TextParser says: IOException found while parsing text file.");
    }
    return hub;
  }
}
