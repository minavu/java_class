package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    String print = "-print";
    String readme = "-readme";
    String airline = "airline";
    String flightNumber = "123";
    String src = "pdx";
    String departDate = "1/1/2022";
    String departTime = "7:00";
    String dest = "nyc";
    String arriveDate = "01/01/2022";
    String arriveTime = "22:59";
    MainMethodResult goodTestCase = invokeMain(print, readme, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void correctNumberOfArgumentsProvided() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
    }

    @Test
    void fewerNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void greaterNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(airline, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void oneOptionProvided() {
        MainMethodResult result = invokeMain(print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void bothOptionProvided() {
        MainMethodResult result = invokeMain(readme, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void moreOptionProvided() {
        MainMethodResult result = invokeMain(print, print, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void flightNumberIsNumber() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, "test", src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void srcAndDestConformToThreeLetterCode() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, flightNumber, "test", departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void dateAndTimeInCorrectFormat() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, flightNumber, src, "test", "test", dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("Incorrect command line arguments."));
    }

    @Test
    void canCreateFlightObject() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
    }

}