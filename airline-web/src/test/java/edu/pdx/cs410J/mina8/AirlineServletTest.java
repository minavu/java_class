package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {
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
  ArrayList<String> goodArgs = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));
  ArrayList<String> goodArgs1 = new ArrayList<>(Arrays.asList(airline, flightNumber1, src, departDate1, departTime1, departAMPM1, dest, arriveDate, arriveTime, arriveAMPM));

  @Test
  void doGetOnNonExistingAirlineWillReturnStatusCode404() throws IOException {
    AirlineServlet servlet = new AirlineServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    String airlineName = "Does Not Exist";
    when(request.getParameter("airline")).thenReturn(airlineName);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  void doPostOnNonExistingAirlineWithValidFlightArgsWillReturnStatusCode200AndNewFlightToString() throws IOException {
    AirlineServlet servlet = new AirlineServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("airline")).thenReturn(airline);
    when(request.getParameter("flightNumber")).thenReturn(flightNumber);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("departDate")).thenReturn(departDate);
    when(request.getParameter("departTime")).thenReturn(departTime);
    when(request.getParameter("departAMPM")).thenReturn(departAMPM);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    Flight newFlight = new Flight(goodArgs);
    StringWriter stringWriter = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(stringWriter, true));

    servlet.doPost(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
    String text = stringWriter.toString();
    assertThat(text, containsString(newFlight.toString()));
  }

  @Test
  void doPostWithInvalidFlightArgsWillReturnStatusCode412() throws IOException {
    AirlineServlet servlet = new AirlineServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    when(request.getParameter("airline")).thenReturn(airline);

    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(response).sendError(eq(HttpServletResponse.SC_PRECONDITION_FAILED), anyString());
  }

  @Test
  void doGetOnExistingAirlineWillReturnStatusCode200AndAllFlights() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(request.getParameter("airline")).thenReturn(airline);
    when(request.getParameter("flightNumber")).thenReturn(flightNumber);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("departDate")).thenReturn(departDate);
    when(request.getParameter("departTime")).thenReturn(departTime);
    when(request.getParameter("departAMPM")).thenReturn(departAMPM);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    when(response.getWriter()).thenReturn(mock(PrintWriter.class));
    servlet.doPost(request, response);

    HttpServletRequest request1 = mock(HttpServletRequest.class);
    HttpServletResponse response1 = mock(HttpServletResponse.class);
    when(request1.getParameter("airline")).thenReturn(airline);

    StringWriter stringWriter = new StringWriter();
    when(response1.getWriter()).thenReturn(new PrintWriter(stringWriter, true));

    servlet.doGet(request1, response1);

    verify(response1).getWriter();
    verify(response1).setStatus(HttpServletResponse.SC_OK);
    String airlineXml = stringWriter.toString();
    assertThat(airlineXml, containsString("airline.dtd"));
  }

  @Test
  void doPostOnExistingAirlineWithValidFlightArgsWillAddNewFlightToAirline() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(request.getParameter("airline")).thenReturn(airline);
    when(request.getParameter("flightNumber")).thenReturn(flightNumber);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("departDate")).thenReturn(departDate);
    when(request.getParameter("departTime")).thenReturn(departTime);
    when(request.getParameter("departAMPM")).thenReturn(departAMPM);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    StringWriter stringWriter = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(stringWriter, true));
    servlet.doPost(request, response);
    verify(response).setStatus(HttpServletResponse.SC_OK);

    HttpServletRequest request1 = mock(HttpServletRequest.class);
    HttpServletResponse response1 = mock(HttpServletResponse.class);
    when(request1.getParameter("airline")).thenReturn(airline);
    when(request1.getParameter("flightNumber")).thenReturn(flightNumber1);
    when(request1.getParameter("src")).thenReturn(src);
    when(request1.getParameter("departDate")).thenReturn(departDate1);
    when(request1.getParameter("departTime")).thenReturn(departTime1);
    when(request1.getParameter("departAMPM")).thenReturn(departAMPM1);
    when(request1.getParameter("dest")).thenReturn(dest);
    when(request1.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request1.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request1.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    StringWriter stringWriter1 = new StringWriter();
    when(response1.getWriter()).thenReturn(new PrintWriter(stringWriter1, true));
    servlet.doPost(request1, response1);
    verify(response1).setStatus(HttpServletResponse.SC_OK);

    HttpServletRequest request2 = mock(HttpServletRequest.class);
    HttpServletResponse response2 = mock(HttpServletResponse.class);
    when(request2.getParameter("airline")).thenReturn(airline);

    StringWriter stringWriter2 = new StringWriter();
    when(response2.getWriter()).thenReturn(new PrintWriter(stringWriter2, true));

    servlet.doGet(request2, response2);

    verify(response2).getWriter();
    verify(response2).setStatus(HttpServletResponse.SC_OK);
    String airlineXml = stringWriter2.toString();
    assertThat(airlineXml, containsString("airline.dtd"));
  }

  @Test
  void doGetWithSrcAndDestParams() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(request.getParameter("airline")).thenReturn(airline);
    when(request.getParameter("flightNumber")).thenReturn(flightNumber);
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("departDate")).thenReturn(departDate);
    when(request.getParameter("departTime")).thenReturn(departTime);
    when(request.getParameter("departAMPM")).thenReturn(departAMPM);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    StringWriter stringWriter = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(stringWriter, true));
    servlet.doPost(request, response);
    verify(response).setStatus(HttpServletResponse.SC_OK);

    HttpServletRequest request1 = mock(HttpServletRequest.class);
    HttpServletResponse response1 = mock(HttpServletResponse.class);
    when(request1.getParameter("airline")).thenReturn(airline);
    when(request1.getParameter("flightNumber")).thenReturn(flightNumber1);
    when(request1.getParameter("src")).thenReturn(src);
    when(request1.getParameter("departDate")).thenReturn(departDate1);
    when(request1.getParameter("departTime")).thenReturn(departTime1);
    when(request1.getParameter("departAMPM")).thenReturn(departAMPM1);
    when(request1.getParameter("dest")).thenReturn(dest);
    when(request1.getParameter("arriveDate")).thenReturn(arriveDate);
    when(request1.getParameter("arriveTime")).thenReturn(arriveTime);
    when(request1.getParameter("arriveAMPM")).thenReturn(arriveAMPM);
    StringWriter stringWriter1 = new StringWriter();
    when(response1.getWriter()).thenReturn(new PrintWriter(stringWriter1, true));
    servlet.doPost(request1, response1);
    verify(response1).setStatus(HttpServletResponse.SC_OK);

    HttpServletRequest request2 = mock(HttpServletRequest.class);
    HttpServletResponse response2 = mock(HttpServletResponse.class);
    when(request2.getParameter("airline")).thenReturn(airline);
    when(request2.getParameter("src")).thenReturn(src);
    when(request2.getParameter("dest")).thenReturn(dest);
    StringWriter stringWriter2 = new StringWriter();
    when(response2.getWriter()).thenReturn(new PrintWriter(stringWriter2, true));
    servlet.doGet(request2, response2);
    verify(response2).getWriter();
    verify(response2).setStatus(HttpServletResponse.SC_OK);
    String airlineXml2 = stringWriter2.toString();
    assertThat(airlineXml2, containsString("airline.dtd"));

    HttpServletRequest request3 = mock(HttpServletRequest.class);
    HttpServletResponse response3 = mock(HttpServletResponse.class);
    when(request3.getParameter("airline")).thenReturn(airline);
    when(request3.getParameter("src")).thenReturn(dest);
    when(request3.getParameter("dest")).thenReturn(src);
    PrintWriter printWriter = mock(PrintWriter.class);
    when(response3.getWriter()).thenReturn(printWriter);
    servlet.doGet(request3, response3);
    verify(response3).sendError(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  @Disabled
  void initiallyServletContainsNoDictionaryEntries() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

  @Test
  @Disabled
  void addOneWordToDictionary() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String word = "TEST WORD";
    String definition = "TEST DEFINITION";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("word")).thenReturn(word);
    when(request.getParameter("definition")).thenReturn(definition);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

//    assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

//    assertThat(servlet.getDefinition(word), equalTo(definition));
  }

}
