package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Methods are provided to perform the
 * three functions of the program.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private static final String PARAM_AIRLINE = "airline";
    private static final String PARAM_FLIGHT_NUMBER = "flightNumber";
    private static final String PARAM_SRC = "src";
    private static final String PARAM_DEPART_DATE = "departDate";
    private static final String PARAM_DEPART_TIME = "departTime";
    private static final String PARAM_DEPART_AMPM = "departAMPM";
    private static final String PARAM_DEST = "dest";
    private static final String PARAM_ARRIVE_DATE = "arriveDate";
    private static final String PARAM_ARRIVE_TIME = "arriveTime";
    private static final String PARAM_ARRIVE_AMPM = "arriveAMPM";
    private static final String[] ALL_PARAMS = {PARAM_AIRLINE, PARAM_FLIGHT_NUMBER, PARAM_SRC, PARAM_DEPART_DATE, PARAM_DEPART_TIME, PARAM_DEPART_AMPM, PARAM_DEST, PARAM_ARRIVE_DATE, PARAM_ARRIVE_TIME, PARAM_ARRIVE_AMPM};

    private final String url;

    /**
     * This is the constructor that takes a host name and a port number to create the URL.
     * The URL is used when connecting to the servlet.
     * @param hostName The hostname of the servlet.
     * @param port The port to connect to the servlet.
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * This method connects to the servlet to perform adding a new flight.  It takes an array of
     * strings to create a map of parameters to pass to the get method.  If the response is an error,
     * this method will throw an exception.  Otherwise, it will return the content as a String.
     * @param args An array of string to be used as parameters.
     * @return The string representation of the new flight.
     * @throws IOException If connection to the servlet cannot be established.
     * @throws RestException If an error occurred while creating the flight.
     */
    public String addNewFlight(String[] args) throws IOException, RestException {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; ++i) {
            params.put(ALL_PARAMS[i], args[i]);
        }
        Response response = post(this.url, params);
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        return response.getContent();
    }

    /**
     * This method connects to the servlet to perform querying of all flights in an airline.  If the
     * airline exists and there are flights, data will be received in xml format.  The client will
     * parse the xml data and create the pretty print version to return as a String.  If the airline
     * doesn't exist, an error will be received.
     * @param airlineName The airline name to query.
     * @return A string representation of the pretty printed airline flights.
     * @throws IOException If an error occurred while connecting to the servlet.
     * @throws ParserException If an error occurred while parsing the xml data.
     * @throws RestException If an error occurred while querying the airline.
     */
    public String queryAirline(String airlineName) throws IOException, ParserException, RestException {
        Response response = get(this.url, Map.of(PARAM_AIRLINE, airlineName));
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        XmlParser xmlParser = new XmlParser(new StringReader(response.getContent()));
        Airline airline = xmlParser.parse(airlineName);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        return prettyPrinter.dumpAid(airline).toString();
    }

    /**
     * This method connects to the servlet and searches an airline for a specified source and destination.
     * If the airline or the parameters does not exist, an error will be received.  If data does exist,
     * xml formatted data will be received and parsed into pretty print format.
     * @param airlineName The airline to search.
     * @param srcName The source code to search.
     * @param destName The destination code to search.
     * @return A string representation of the retrieved data.
     * @throws IOException If a connection cannot be established with the servlet.
     * @throws ParserException If the xml data cannot be parsed.
     * @throws RestException If an error occurred while searching for the parameters.
     */
    public String searchAirlineSrcDest(String airlineName, String srcName, String destName) throws IOException, ParserException, RestException {
        Response response = get(this.url, Map.of(PARAM_AIRLINE, airlineName, PARAM_SRC, srcName, PARAM_DEST, destName));
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        XmlParser xmlParser = new XmlParser(new StringReader(response.getContent()));
        Airline airline = xmlParser.parse(airlineName);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        return prettyPrinter.dumpAid(airline).toString();
    }

    /**
     * This method checks the response code of the response from the servlet and throws an exception
     * if the code is not 200.
     * @param response The response from the servlet.
     * @throws RestException If an error occurred as indicated by the servlet.
     */
    private void throwExceptionIfNotOkayHttpStatusOtherwiseContinue(Response response) throws RestException {
        int code = response.getCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

}
