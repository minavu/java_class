package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * This class reads a text file and parses the data to create an Airline with
 * all associated flights.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  /**
   * This is a constructor that takes a Reader object.
   * @param reader -A Reader object containing Airline and Flight data.
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * This is a constructor that takes a string representing the file path where
   * the Airline data is stored.
   * @param filename -A string representing the file path.
   * @throws FileNotFoundException -If the file path and file name does not exist.
   */
  public TextParser(String filename) throws FileNotFoundException {
    this.reader = new FileReader(filename);
  }

  /**
   * This method parses each line in the file to create an Airline and add all the
   * associated flights. Data stored in file are expected to be separated by the "|" delimiter.
   * @return -An Airline with all flights as stored in the file.
   * @throws ParserException -If the source file is malformed.
   * @throws IllegalArgumentException -If any of the arguments to create the flights is formatted incorrectly.
   */
  @Override
  public Airline parse() throws ParserException, IllegalArgumentException {
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
      throw new IllegalArgumentException("Error when parsing the file: " + e.getMessage());
    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }

  /**
   * This method first checks that the Airline name of the file matches the Airline name of the flight to be added
   * before it parses each line in the file to create an Airline and add all the
   * associated flights. Data stored in file are expected to be separated by the "|" delimiter.
   * @param matchAirlineName -A string representing the airline of the new flight to be added.
   * @return -An Airline with all flights as stored in the file.
   * @throws ParserException -If the source file is malformed.
   * @throws IllegalArgumentException -If any of the arguments to create the flights is formatted incorrectly.
   */
  public Airline parse(String matchAirlineName) throws IllegalArgumentException, ParserException, InputMismatchException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {
      Airline airline = null;
      String airlineName = br.readLine();
      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }
      String[] parsedLine = airlineName.split("\\|");
      String airlineNameInFile = parsedLine[0].substring(0,1).toUpperCase() + parsedLine[0].substring(1).toLowerCase();
      if (!airlineNameInFile.equals(matchAirlineName)) {
        throw new InputMismatchException("Airline name in text file does not match \"" + matchAirlineName + "\". It is " + parsedLine[0] + ".");
      }
      airline = new Airline(airlineNameInFile);
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
      throw new IllegalArgumentException("Error when parsing the file: " + e.getMessage());
    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
