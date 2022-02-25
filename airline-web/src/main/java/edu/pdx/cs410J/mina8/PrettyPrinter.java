package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * This class handles pretty printing of an airline and all associated flights.
 */

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    /**
     * This is a constructor that takes a String as an argument.
     * @param fileName A string representing the file name.
     * @throws IOException if the file cannot be found.
     */
    public PrettyPrinter(String fileName) throws IOException {
        this.writer = new FileWriter(fileName);
    }

    /**
     * This is the default constructor.
     */
    public PrettyPrinter() {
        this.writer = null;
    }

    /**
     * This method prints the airline with all flights to standard output.
     * @param airline An airline with flights.
     */
    @Override
    public void dump(Airline airline) {
        System.out.println(dumpAid(airline));
    }

    /**
     * This method aids the dump method by returning the items to print in a variable for unit testing.
     * @param airline An airline with flights.
     * @return A StringBuilder object containing pretty printed information.
     */
    public StringBuilder dumpAid(Airline airline) {
        StringBuilder output = new StringBuilder("Printing all flight information for " + airline.getName() + " Airline.\n"
                + "Flights ordered by departing airport and departure date/time.\n\n");
        Collection<Flight> flights = airline.getFlights();
        flights.forEach((flight) -> {
            int flightNumber = flight.getNumber();
            String src = flight.getSource();
            String srcName = AirportNames.getName(flight.getSource());
            String departure = flight.getDepartureString();
            String dest = flight.getDestination();
            String destName = AirportNames.getName(flight.getDestination());
            String arrival = flight.getArrivalString();
            String duration = flight.getFlightDuration();
            output.append(String.format("%-4s->%4s %8s  %d\n", src, dest, "Flight", flightNumber));
            output.append(String.format("%10s %8s  %s at %s\n", "", "Depart", srcName, departure));
            output.append(String.format("%10s %8s  %s at %s\n", "", "Arrive", destName, arrival));
            output.append(String.format("%10s %8s  %s\n\n", "", "Duration", duration));
        });
        return output;
    }

    /**
     * This method prints the airline with all flights to a file.
     * @param airline An airline with flights.
     * @param fileName The file to write to.
     */
    public void dump(Airline airline, String fileName) {
        try (PrintWriter pw = new PrintWriter(this.writer)) {
            pw.println(dumpAid(airline));
            pw.flush();
        }
    }
}
