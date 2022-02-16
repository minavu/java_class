package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.InputMismatchException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {
  String fileWithValidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithValidArgs.txt";
  String fileWithInvalidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithInvalidArgs.txt";

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
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithValidArgsTxt);
    } catch (FileNotFoundException e) {
    }
    assertThat(parser, notNullValue());
  }

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

  @Test
  void fileWithInvalidFlightInfoWillThrowExceptionWhenCreatingFlight() {
    TextParser parser = null;
    try {
      parser = new TextParser(fileWithInvalidArgsTxt);
    } catch (FileNotFoundException e) {
    }
    assertThrows(IllegalArgumentException.class, parser::parse);
  }

  @Test
  void parsingFileWithAirlineNameDifferentThanNewFlightInfoWillThrowException() throws ParserException {
    assertThrows(InputMismatchException.class, () -> (new TextParser(fileWithValidArgsTxt)).parse("notAnAirlineName"));
  }
}
