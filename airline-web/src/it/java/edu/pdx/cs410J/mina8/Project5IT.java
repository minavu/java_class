package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
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

    @Test
    void noCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project5.MISSING_ARGS));
    }

    @Test
    void readmeOptionHandled() {
        MainMethodResult result = invokeMain(Project5.class, readmeOptionOnly);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Developer:      Mina Vu"));
    }

    @Test
    void onlyHostOptionProvided() {
        MainMethodResult result = invokeMain(Project5.class, "-host", "localhost");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("See below for usage guide"));
    }

    @Test
    void onlyPortOptionProvided() {
        MainMethodResult result = invokeMain(Project5.class, "-port", "8080");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("See below for usage guide"));

        result = invokeMain(Project5.class, "-port", "localhost");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("See below for usage guide"));
    }

    @Test
    void addNewFlightHandledAndSearchAirlineHandled() {
        String[] hostPortWithParamsAndPrintNewFlight = {"-host", HOSTNAME, "-port", PORT, "-print", airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm};
        Flight newFlight = new Flight(new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm)));
        MainMethodResult result = invokeMain(Project5.class, hostPortWithParamsAndPrintNewFlight);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString(newFlight.toString()));

        result = invokeMain(Project5.class, hostPortSearchWithParams);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Printing all flight information"));
    }

}