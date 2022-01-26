package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     * @return          The output of the main method invocation.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
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
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect number of command line arguments."));
    }

    /**
     * Tests that invoking the main method with the correct number of arguments does not issue an error.
     */
    @Test
    void correctNumberOfArgumentsProvided() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
    }

    /**
     * Tests that invoking the main method with fewer number of arguments issues an error and message.
     */
    @Test
    void fewerNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect number of command line arguments."));
    }

    /**
     * Tests that invoking the main method with greater number of arguments issues an error and message.
     */
    @Test
    void greaterNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(airline, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Incorrect number of command line arguments."));
    }

    /**
     * Tests that invoking the main method with one option tag issues no error.
     */
    @Test
    void oneOptionProvided() {
        MainMethodResult result = invokeMain(print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests that invoking the main method with both option tags issues no error.
     */
    @Test
    void bothOptionProvided() {
        MainMethodResult result = invokeMain(readme, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests that invoking the main method with more options issues an error and message.
     */
    @Test
    void moreOptionProvided() {
        MainMethodResult result = invokeMain(print, print, print, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("There are too many options present."));
    }

    /**
     * Tests that invoking the main method with a flight number that isn't a number issues an error
     * and a message.  If the flight number is a number, no error is issued.
     */
    @Test
    void flightNumberIsNumber() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, "test", src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("The given flight number is not a number."));
    }

    /**
     * Tests that the arguments for src and dest must be correct when invoking the main method.
     */
    @Test
    void srcAndDestConformToThreeLetterCode() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, flightNumber, "test", departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("code must be three letters."));
    }

    /**
     * Tests that the date and time arguments are in correct format.
     */
    @Test
    void dateAndTimeInCorrectFormat() {
        assertThat(goodTestCase.getExitCode(), equalTo(0));
        MainMethodResult result2 = invokeMain(airline, flightNumber, src, "test", "test", dest, arriveDate, arriveTime);
        assertThat(result2.getExitCode(), equalTo(1));
        assertThat(result2.getTextWrittenToStandardError(), containsString("date and time is not in an acceptable format."));
    }
}