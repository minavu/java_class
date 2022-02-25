package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    private AirlineRestClient newAirlineRestClient() {
        int port = Integer.parseInt(PORT);
        return new AirlineRestClient(HOSTNAME, port);
    }

    String airline = "airline";
    String flightNumber = "123";
    String flightNumber1 = "456";
    String src = "pdx";
    String departDate = "1/1/2022";
    String departDate1 = "2/1/2022";
    String departTime = "7:00";
    String departTime1 = "8:00";
    String departAMPM = "AM";
    String departAMPM1 = "PM";
    String dest = "hnl";
    String arriveDate = "02/02/2022";
    String arriveTime = "4:15";
    String arriveAMPM = "PM";
    String[] goodArgs = {airline, flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM};
    String[] badArgs = {airline, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM};
    String[] goodArgs1 = {airline, flightNumber1, src, departDate1, departTime1, departAMPM1, dest, arriveDate, arriveTime, arriveAMPM};
    String[] goodArgs2 = {"Different Airline", flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM};
    String[] goodArgs3 = {airline, "789", "mia", departDate1, departTime1, departAMPM1, dest, arriveDate, arriveTime, arriveAMPM};

    @Test
    void createNewFlightWithValidArgsWillReceiveFlightToString() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        String newFlightStr = client.addNewFlight(goodArgs);
        Flight newFlight = new Flight(new ArrayList<>(Arrays.asList(goodArgs)));
        assertThat(newFlightStr, equalTo(newFlight.toString()));
    }

    @Test
    void createNewFlightWithInvalidArgsWillThrowException() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        HttpRequestHelper.RestException e = assertThrows(HttpRequestHelper.RestException.class, () -> client.addNewFlight(badArgs));
        assertThat(e.getMessage(), containsString(""));
    }

    @Test
    void queryingNonExistingAirlineWillThrowException() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        HttpRequestHelper.RestException e = assertThrows(HttpRequestHelper.RestException.class, () -> client.queryAirline("Does Not Exist"));
        assertThat(e.getMessage(), containsString(""));
    }

    @Test
    void queryingExistingAirlineWillReceiveAllFlights() throws IOException, ParserException {
        AirlineRestClient client = newAirlineRestClient();
        String newFlightStr = client.addNewFlight(goodArgs);
        Flight newFlight = new Flight(new ArrayList<>(Arrays.asList(goodArgs)));
        assertThat(newFlightStr, equalTo(newFlight.toString()));
        String newFlightStr1 = client.addNewFlight(goodArgs1);
        Flight newFlight1 = new Flight(new ArrayList<>(Arrays.asList(goodArgs1)));
        assertThat(newFlightStr1, equalTo(newFlight1.toString()));
        String newFlightStr2 = client.addNewFlight(goodArgs2);
        Flight newFlight2 = new Flight(new ArrayList<>(Arrays.asList(goodArgs2)));
        assertThat(newFlightStr2, equalTo(newFlight2.toString()));

        String airlineData = client.queryAirline(airline);
        assertThat(airlineData, containsString("Printing all flight information for " + airline));

        String airlineData1 = client.queryAirline("Different Airline");
        assertThat(airlineData1, containsString("Printing all flight information for Different Airline"));
    }

    @Test
    void queryingSrcAndDestOfExistingAirlineWithSrcAndDestWillReceiveThoseFlights() throws IOException, ParserException {
        AirlineRestClient client = newAirlineRestClient();
        String newFlightStr = client.addNewFlight(goodArgs);
        Flight newFlight = new Flight(new ArrayList<>(Arrays.asList(goodArgs)));
        assertThat(newFlightStr, equalTo(newFlight.toString()));
        String newFlightStr1 = client.addNewFlight(goodArgs1);
        Flight newFlight1 = new Flight(new ArrayList<>(Arrays.asList(goodArgs1)));
        assertThat(newFlightStr1, equalTo(newFlight1.toString()));
        String newFlightStr2 = client.addNewFlight(goodArgs2);
        Flight newFlight2 = new Flight(new ArrayList<>(Arrays.asList(goodArgs2)));
        assertThat(newFlightStr2, equalTo(newFlight2.toString()));
        String newFlightStr3 = client.addNewFlight(goodArgs3);
        Flight newFlight3 = new Flight(new ArrayList<>(Arrays.asList(goodArgs3)));
        assertThat(newFlightStr3, equalTo(newFlight3.toString()));

        String airlineData = client.searchAirlineSrcDest(airline, src, dest);
        assertThat(airlineData, containsString("Printing all flight information for " + airline));

        String airlineData2 = client.searchAirlineSrcDest("Different Airline", src, dest);
        assertThat(airlineData2, containsString("Printing all flight information for Different Airline"));

        String airlineData3 = client.searchAirlineSrcDest(airline, "mia", dest);
        assertThat(airlineData3, containsString("Printing all flight information for " + airline));
    }
}
