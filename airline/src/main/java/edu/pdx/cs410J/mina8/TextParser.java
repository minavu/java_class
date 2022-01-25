package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  public TextParser(String filename) throws FileNotFoundException {
    this.reader = new FileReader(filename);
  }

  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      Airline airline = null;

      String airlineName = br.readLine();
      String[] parsedLine = airlineName.split("\\|");

      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }

      airline = new Airline(parsedLine[0]);
      Flight flight = new Flight(new ArrayList<>(Arrays.asList(parsedLine)));
      airline.addFlight(flight);
      while (br.ready()) {
        airlineName = br.readLine();
        parsedLine = airlineName.split("\\|");
        flight = new Flight(new ArrayList<>(Arrays.asList(parsedLine)));
        airline.addFlight(flight);
      }
      return airline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
