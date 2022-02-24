package edu.pdx.cs410J.mina8;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AirlineServlet extends HttpServlet {
  private final Collection<Airline> hub = new TreeSet<>();

  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = request.getParameter("airline");
      String srcName = request.getParameter("src");
      String destName = request.getParameter("dest");
      Airline airline = new Airline(airlineName);

      if (!hub.contains(airline)) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
              response.sendError(HttpServletResponse.SC_NOT_FOUND);
          } else {
              PrintWriter printWriter = response.getWriter();
              XmlDumper xmlDumper = new XmlDumper(printWriter);
              xmlDumper.dump(curatedAirline);
              printWriter.flush();
              response.setStatus(HttpServletResponse.SC_OK);
          }
      }
  }

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
