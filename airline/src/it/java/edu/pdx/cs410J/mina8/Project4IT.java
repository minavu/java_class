package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Project4IT extends InvokeMainTestCase {

    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args );
    }

    String print = "-print";
    String readme = "-readme";
    String textFile = "-textFile";
    String xmlFile = "-xmlFile";
    String pretty = "-pretty";
    String airline = "Airline";
    String flightNumber = "123";
    String src = "pdx";
    String departDate = "1/1/2022";
    String departTime = "7:00";
    String departAmPm = "am";
    String dest = "hnl";
    String arriveDate = "01/02/2022";
    String arriveTime = "2:59";
    String arriveAmPm = "pm";
    MainMethodResult goodTestCaseForP4 = invokeMain(print, airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm);

    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void correctNumberOfArgumentsProvided() {
        assertThat(goodTestCaseForP4.getExitCode(), equalTo(0));
    }

    @Test
    void fewerNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(flightNumber);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("See below for usage guide."));
    }

    @Test
    void greaterNumberOfArgumentsProvided() {
        MainMethodResult result = invokeMain(airline, airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm);
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void unknownOptionProvided() {
        MainMethodResult result = invokeMain("-notAnOption", airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("not a valid option."));
    }

    @Test
    void optionPrettyIsHandledCorrectly() {
        MainMethodResult result = invokeMain(pretty, airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm);
        assertThat(result.getExitCode(), equalTo(1));

        MainMethodResult result1 = invokeMain(pretty, "-", airline, flightNumber, src, departDate, departTime, departAmPm, dest, arriveDate, arriveTime, arriveAmPm);
        assertThat(result1.getExitCode(), equalTo(0));
        assertThat(result1.getTextWrittenToStandardOut(), containsString("Printing all flight information for " + airline));
    }
}