package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;

//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * These are unit tests for the TextParser class.
 */
public class TextParserTest {
  String tempFilesToDeleteDir = "src/test/resources/edu/pdx/cs410J/mina8/tempFilesToDelete/";
  String fileWithValidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithValidArgs.txt";
  String fileWithInvalidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithInvalidArgs.txt";

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
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithValidArgsTxt);
    } catch (FileNotFoundException e) {
    }
    assertThat(parser, notNullValue());
  }

  /**
   * Tests that an existing file that uses the appropriate delimiter can be read.
   */
  @Test
  void existingFileCanBeReadUsingDelimiter() {
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithValidArgsTxt);
    } catch (FileNotFoundException e) {
    }
    Airline airline = null;
    try {
      airline = parser.parse("Abc");
    } catch (ParserException | IllegalArgumentException | InputMismatchException e) {
    }
    assertThat(airline.getName(), equalTo("Abc"));
  }

  /**
   * Tests that an Airline can be created with multiple flights if present in the file.
   */
  @Test
  void fileWithMultipleFlightsCanAllBeCreatedAndAddedToAirline() {
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithValidArgsTxt);
    } catch (FileNotFoundException e) {
    }
    Airline airline = null;
    try {
      airline = parser.parse("Abc");
    } catch (ParserException | IllegalArgumentException | InputMismatchException e) {
    }
    Collection<Flight> flights = airline.getFlights();
    assertThat(flights.size(), greaterThan(1));
  }

  /**
   * Tests that a file containing invalid Flight data will throw an exception.
   */
  @Test
  void fileWithInvalidFlightInfoWillThrowExceptionWhenCreatingFlight() {
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithInvalidArgsTxt);
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
    assertThrows(InputMismatchException.class, () -> (new TextParser(fileWithInvalidArgsTxt)).parse("notAnAirlineName"));
  }
}
