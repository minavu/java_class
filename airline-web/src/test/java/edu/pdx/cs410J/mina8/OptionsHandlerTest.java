package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptionsHandlerTest {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");
  String airline = "airline";
  String flightNumber = "123";
  String src = "pdx";
  String departDate = "1/1/2022";
  String departTime = "7:00";
  String departAmPm = "am";
  String dest = "hnl";
  String arriveDate = "01/02/2022";
  String arriveTime = "2:59";
  String arriveAmPm = "pm";

  String[] readmeOptionOnly = {"-README"};
  String[] hostPortOnly = {"-host", "-port"};
  String[] hostPortWithParams = {"-host", HOSTNAME, "-port", PORT};
  String[] hostPortSearchWithParams = {"-host", HOSTNAME, "-port", PORT, "-search", airline, src, dest};
  String[] hostPortWithParamsAndNewFlight = {"-host", HOSTNAME, "-port", PORT, airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm};

  ArrayList<String> argsList = null;
  OptionsHandler optionsHandlerTester = new OptionsHandler();

  @Test
  void canHandleReadMeOptionAndExit() {

  }

}
