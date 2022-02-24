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
  AirlineServlet servlet = new AirlineServlet();
  HttpServletRequest request = mock(HttpServletRequest.class);
  HttpServletResponse response = mock(HttpServletResponse.class);

  String airline = "airline";
  String flightNumber = "123";
  String src = "pdx";
  String departDate = "1/1/2022";
  String departTime = "7:00";
  String departAMPM = "AM";
  String dest = "hnl";
  String arriveDate = "01/01/2022";
  String arriveTime = "2:59";
  String arriveAMPM = "PM";
  ArrayList<String> goodArgs = new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM));

  @Test
  void doGetOnNonExistingAirlineWillReturnStatusCode404() throws IOException {
    String airlineName = "Does Not Exist";
    when(request.getParameter("airline")).thenReturn(airlineName);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  void doPostWithValidFlightArgsWillReturnStatusCode200() throws IOException {
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
    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

//  @Test
//  void doGetOnExistingAirlineWillReturnStatusCode200() throws IOException {
//    String airlineName = "Does Exist";
//    when(request.getParameter("airline")).thenReturn(airlineName)
//  }

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

    assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

//    assertThat(servlet.getDefinition(word), equalTo(definition));
  }

}
