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
//    try ( BufferedReader br = new BufferedReader(this.reader) ) {
//
//      Airline airline = null;
//      String airlineName = br.readLine();
//      if (airlineName == null) {
//        throw new ParserException("Missing airline name");
//      }
//      String[] parsedLine = airlineName.split("\\|");
//      airline = new Airline(parsedLine[0]);
//      Flight flight = new Flight(new ArrayList<>(Arrays.asList(parsedLine)));
//      airline.addFlight(flight);
//
//      while (br.ready()) {
//        airlineName = br.readLine();
//        parsedLine = airlineName.split("\\|");
//        flight = new Flight(new ArrayList<>(Arrays.asList(parsedLine)));
//        airline.addFlight(flight);
//      }
//      return airline;
//
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("TextParser says: Error when parsing the file: " + e.getMessage());
//    } catch (IOException e) {
//      throw new ParserException("TextParser says: IOException found while parsing text file.");
//    }
    return null;
  }

  public TreeSet<Airline> parseHub() throws ParserException, IllegalArgumentException {
    TreeSet<Airline> hub = new TreeSet<>();
    try ( BufferedReader br = new BufferedReader(this.reader) ) {

      Airline airline = null;
      do {
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

      } while (br.ready());

    } catch (IOException e) {
      throw new ParserException("TextParser says: IOException found while parsing text file.");
    }
    return hub;
  }
}
