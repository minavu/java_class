package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
  String dest = "nyc";
  String arriveDate = "01/01/2022";
  String arriveTime = "22:59";
  ArrayList<String> args = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime));
  Flight flight = new Flight(args);

  /**
   * Tests that method in abstract class returns null.
   */
  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  /**
   * Tests that flight number given through argument is in the flight number field.
   */
  @Test
  void flightNumberIsGivenIdNumber() {
    assertThat(flight.getNumber(), equalTo(Integer.parseInt(flightNumber)));
  }

  /**
   * Tests that the src given through argument is in the src field.
   */
  @Test
  void srcIsGivenCode() {
    assertThat(flight.getSource(), containsStringIgnoringCase(src));
  }

  /**
   * Tests that the dest given through argument is in the dest field.
   */
  @Test
  void destIsGivenCode() {
    assertThat(flight.getDestination(), containsStringIgnoringCase(dest));
  }

  /**
   * Tests that the departure date and time is in the depart field.
   */
  @Test
  void departIncludesDateAndTimeGiven() {
    assertThat(flight.getDepartureString(), containsStringIgnoringCase(departDate + " " + departTime));
  }

  /**
   * Tests that the arrival date and time is in the arrive field.
   */
  @Test
  void arriveIncludesDateAndTimeGiven() {
    assertThat(flight.getArrivalString(), containsStringIgnoringCase(arriveDate + " " + arriveTime));
  }

}
