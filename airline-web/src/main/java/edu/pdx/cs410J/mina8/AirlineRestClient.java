package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
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

    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    public String addNewFlight(String[] args) throws IOException, RestException {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; ++i) {
            params.put(ALL_PARAMS[i], args[i]);
        }
        Response response = post(this.url, params);
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        return response.getContent();
    }

    public String queryAirline(String airlineName) throws IOException, ParserException, RestException {
        Response response = get(this.url, Map.of(PARAM_AIRLINE, airlineName));
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        XmlParser xmlParser = new XmlParser(new StringReader(response.getContent()));
        Airline airline = xmlParser.parse(airlineName);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        return prettyPrinter.dumpAid(airline).toString();
    }

    public String searchAirlineSrcDest(String airlineName, String srcName, String destName) throws IOException, ParserException, RestException {
        Response response = get(this.url, Map.of(PARAM_AIRLINE, airlineName, PARAM_SRC, srcName, PARAM_DEST, destName));
        throwExceptionIfNotOkayHttpStatusOtherwiseContinue(response);
        XmlParser xmlParser = new XmlParser(new StringReader(response.getContent()));
        Airline airline = xmlParser.parse(airlineName);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        return prettyPrinter.dumpAid(airline).toString();
    }

    private void throwExceptionIfNotOkayHttpStatusOtherwiseContinue(Response response) throws RestException {
        int code = response.getCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

}
