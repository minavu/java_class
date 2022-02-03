package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  String airline = "airline";
  String flightNumber = "123";
  String src = "pdx";
  String departDate = "1/1/2022";
  String departTime = "7:00";
  String departAMPM = "AM";
  String dest = "hnl";
  String arriveDate = "01/01/2022";
  String arriveTime = "2:59";
  String arriveAMPM = "PM";
  ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
  Flight flightTester = new Flight(args);

  /**
   * Tests that departure field is a Date object.
   */
  @Test
  void departIsOfDateClass() {
    assertThat(flightTester.getDeparture(), instanceOf(Date.class));
  }

  /**
   * Tests that flight number given through argument is in the flight number field.
   */
  @Test
  void flightNumberIsGivenIdNumber() {
    assertThat(flightTester.getNumber(), equalTo(Integer.parseInt(flightNumber)));
  }

  /**
   * Tests that the src given through argument is in the src field.
   */
  @Test
  void srcIsGivenCode() {
    assertThat(flightTester.getSource(), containsStringIgnoringCase(src));
  }

  /**
   * Tests that the dest given through argument is in the dest field.
   */
  @Test
  void destIsGivenCode() {
    assertThat(flightTester.getDestination(), containsStringIgnoringCase(dest));
  }

  /**
   * Tests that an empty flight can be created.
   */
  @Test
  void emptyFlightCanBeCreated() {
    Flight flight = new Flight();
    assertThat(flight, instanceOf(Flight.class));
  }

  /**
   * Tests that an exception is thrown when the flight number is incorrect.
   */
  @Test
  void flightNumberParsingWillThrowExceptionForNonNumberFlightNumbers() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, "abc", src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the source code is not know is incorrect.
   */
  @Test
  void wrongAirportCodeWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, "nyc", departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the source code is not know is incorrect.
   */
  @Test
  void wrong2AirportCodeWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, "nyc", arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the departure date is incorrect.
   */
  @Test
  void wrongDepartDateFormatWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, "1/1/22", departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the arrival date is incorrect.
   */
  @Test
  void wrongArriveDateFormatWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, dest, "1/1/22", arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the source code is incorrect.
   */
  @Test
  void wrongSourceCodeLengthWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, "pd", departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the source code is incorrect.
   */
  @Test
  void wrong2SourceCodeLengthWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, "pd3", departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the destination code is incorrect.
   */
  @Test
  void wrongDestCodeLengthWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, "pd", arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }

  /**
   * Tests that an exception is thrown when the destination code is incorrect.
   */
  @Test
  void wrong2DestCodeLengthWillThrowException() {
    ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, "pd2", arriveDate, arriveTime, arriveAMPM));
    assertThrows(IllegalArgumentException.class, () -> new Flight(args));
  }
}
