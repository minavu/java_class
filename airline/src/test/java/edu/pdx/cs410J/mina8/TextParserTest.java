package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;

//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * These are unit tests for the TextParser class.
 */
public class TextParserTest {

  /**
   * Tests that a non-existent file name will throw an exception.
   */
  @Test
  void nonexistentFileNameThrowsFileNotFoundException() {
    String filename = "notAFile.txt";
    assertThrows(FileNotFoundException.class, () -> new TextParser(filename));
  }

  /**
   * Tests that an existing file name can be accessed.
   */
  @Test
  void existingFileNameCanBeAccessed() {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithValidArgs.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    assertThat(parser, notNullValue());
  }

  /**
   * Tests that an existing file that uses the appropriate delimiter can be read.
   */
  @Test
  void existingFileCanBeReadUsingDelimiter() {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithValidArgs.txt";
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

  /**
   * Tests that a Flight can be created when a file contains valid data.
   */
  @Test
  void fileWithValidFlightInfoCanCreateANewFlight() {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithValidArgs.txt";
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

  /**
   * Tests that an Airline can be created with multiple flights if present in the file.
   */
  @Test
  void fileWithMultipleFlightsCanAllBeCreatedAndAddedToAirline() {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithValidArgs.txt";
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

  /**
   * Tests that a file containing invalid Flight data will throw an exception.
   */
  @Test
  void fileWithInvalidFlightInfoWillThrowExceptionWhenCreatingFlight() {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithInvalidArgs.txt";
    TextParser parser = null;
    try {
      parser = new TextParser(filename);
    } catch (FileNotFoundException e) {
    }
    assertThrows(IllegalArgumentException.class, parser::parse);
  }

  /**
   * Tests that an exception will be thrown when the Airline name in file and of new Flight does not match.
   * @throws ParserException
   */
  @Test
  void parsingFileWithAirlineNameDifferentThanNewFlightInfoWillThrowException() throws ParserException {
    String filename = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithInvalidArgs.txt";
    assertThrows(InputMismatchException.class, () -> (new TextParser(filename)).parse("notAnAirlineName"));
  }
}
