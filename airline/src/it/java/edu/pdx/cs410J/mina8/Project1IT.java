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
    String src = "src";
    String departDate = "departDate";
    String departTime = "departTime";
    String dest = "dest";
    String arriveDate = "arriveDate";
    String arriveTime = "arriveTime";

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void correctNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getTextWrittenToStandardOut(), containsString("8"));
    }

    @Test
    void fewerNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void greaterNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(airline, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void oneOptionProvided() {
        MainMethodResult result = invokeMain(print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getTextWrittenToStandardOut(), containsString("1"));
    }

    @Test
    void bothOptionProvided() {
        MainMethodResult result = invokeMain(readme, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getTextWrittenToStandardOut(), containsString("2"));
    }

    @Test
    void moreOptionProvided() {
        MainMethodResult result = invokeMain(readme, readme, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many options rendered."));
    }

//    @Test
//    void flightNumberCanConvertToInteger() {
//        MainMethodResult result = invokeMain(readme, print, airline, flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);
//
//    }
}