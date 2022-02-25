package edu.pdx.cs410J.mina8;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This is the AirlineServlet class that will route the different requests from the client.
 * Depending on the correctness of the request, the servlet will create a response to send back.
 */
public class AirlineServlet extends HttpServlet {
  private final Collection<Airline> hub = new TreeSet<>();

    /**
     * This is the route that all GET methods will encounter.  Depending on the parameters in
     * the request, the servlet will either grab all flights or grab a subset of flights in an
     * existing airline and return to the client.  If the airline does not exist, error code 404
     * will be sent.
     * @param request The client request that contains the parameters.
     * @param response The servlet response that contains the data or an error code.
     * @throws IOException If there is a connection error.
     */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = request.getParameter("airline");
      String srcName = request.getParameter("src");
      String destName = request.getParameter("dest");
      Airline airline = new Airline(airlineName);

      if (!hub.contains(airline)) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND, airlineName + " airline does not exist.");
          return;
      }

      Airline foundAirline = null;
      for (Airline a : hub) {
          if (a.getName().equals(airline.getName())) {
              foundAirline = a;
              break;
          }
      }

      if (srcName == null && destName == null) {
          PrintWriter printWriter = response.getWriter();
          XmlDumper xmlDumper = new XmlDumper(printWriter);
          assert foundAirline != null;
          xmlDumper.dump(foundAirline);
          printWriter.flush();
          response.setStatus(HttpServletResponse.SC_OK);
      } else {
          Airline curatedAirline = new Airline(foundAirline.getName());
          Collection<Flight> flights = foundAirline.getFlights();
          for (Flight f : flights) {
              if (f.getSource().equalsIgnoreCase(srcName) && f.getDestination().equalsIgnoreCase(destName)) {
                  curatedAirline.addFlight(f);
              }
          }
          if (curatedAirline.getFlights().size() == 0) {
              response.sendError(HttpServletResponse.SC_NOT_FOUND, airlineName + " has no flights from " + srcName + " to " + destName + ".");
          } else {
              PrintWriter printWriter = response.getWriter();
              XmlDumper xmlDumper = new XmlDumper(printWriter);
              xmlDumper.dump(curatedAirline);
              printWriter.flush();
              response.setStatus(HttpServletResponse.SC_OK);
          }
      }
  }

    /**
     * This is the route that all POST method will encounter.  Depending on the parameters,
     * the servlet will attempt to create a new flight and add to an existing or new airline.
     * If the parameters do not allow the successful creation of a new flight, servlet will
     * send error code 412.
     * @param request The client request that contains the parameters.
     * @param response The servlet response that contains the data or an error code.
     * @throws IOException If there is a connection error.
     */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline = request.getParameter("airline");
      String flightNumber = request.getParameter("flightNumber");
      String src = request.getParameter("src");
      String departDate = request.getParameter("departDate");
      String departTime = request.getParameter("departTime");
      String departAMPM = request.getParameter("departAMPM");
      String dest = request.getParameter("dest");
      String arriveDate = request.getParameter("arriveDate");
      String arriveTime = request.getParameter("arriveTime");
      String arriveAMPM = request.getParameter("arriveAMPM");

      try {
          Airline newAirline = new Airline(airline);
          Flight newFlight = new Flight(new ArrayList<>(Arrays.asList(airline, flightNumber, src, departDate, departTime, departAMPM, dest, arriveDate, arriveTime, arriveAMPM)));
          if (!hub.contains(newAirline)) {
              newAirline.addFlight(newFlight);
              hub.add(newAirline);
          } else {
              for (Airline a : hub) {
                  if (a.getName().equals(newAirline.getName())) {
                      a.addFlight(newFlight);
                      break;
                  }
              }
          }

          PrintWriter printWriter = response.getWriter();
          printWriter.println(newFlight);
          printWriter.flush();
          response.setStatus(HttpServletResponse.SC_OK);
      } catch (IllegalArgumentException e) {
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
      }
  }
}
