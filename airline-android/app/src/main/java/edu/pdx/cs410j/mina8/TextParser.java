package edu.pdx.cs410j.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  public TextParser(String filename) throws FileNotFoundException {
    this.reader = new FileReader(filename);
  }

  @Override
  public Airline parse() throws ParserException, IllegalArgumentException {
    return parseHelper();
  }

  public Airline parse(String matchAirlineName) throws IllegalArgumentException, ParserException, InputMismatchException {
    Airline airline = parseHelper();
    if (!airline.getName().equals(matchAirlineName)) {
      throw new InputMismatchException("TextParser says: Airline name in text file does not match \"" + matchAirlineName + "\". It is " + airline.getName() + ".");
    }
    return airline;
  }

  public Airline parseHelper() throws ParserException, IllegalArgumentException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {
      Airline airline = null;
      String airlineName = br.readLine();
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      String[] parsedLine = airlineName.split("\\|");
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
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("TextParser says: Error when parsing the file: " + e.getMessage());
    } catch (IOException e) {
      throw new ParserException("TextParser says: IOException found while parsing text file.");
    }
  }
}
