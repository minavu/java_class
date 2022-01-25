package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void nonexistentFileNameThrowsFileNotFoundException() {
    String filename = "notAFile.txt";
    assertThrows(FileNotFoundException.class, () -> new TextParser(filename));
  }

  @Test
  void existingFileNameCanBeAccessed() {
    String filename = "resources/isAFile.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    assertThat(parser, notNullValue());
  }

  @Test
  void existingFileCanBeReadUsingDelimiter() {
    String filename = "resources/isAFile.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    Airline airline = null;
    try {
      airline = parser.parse();
    } catch (ParserException e) {
    }
    assertThat(airline.getName(), equalTo("abc"));
  }

  @Test
  void fileWithValidFlightInfoCanCreateANewFlight() {
    String filename = "resources/isAFile.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    Airline airline = null;
    try {
      airline = parser.parse();
    } catch (ParserException e) {
    }
    ArrayList<Flight> flights = (ArrayList<Flight>) airline.getFlights();
    assertThat(flights.get(0).toString(), containsString("Flight 123"));
  }

  @Test
  void fileWithMultipleFlightsCanAllBeCreatedAndAddedToAirline() {
    String filename = "resources/isAFile.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    Airline airline = null;
    try {
      airline = parser.parse();
    } catch (ParserException e) {
    }
    ArrayList<Flight> flights = (ArrayList<Flight>) airline.getFlights();
    assertThat(flights.size(), greaterThan(1));
  }
}
