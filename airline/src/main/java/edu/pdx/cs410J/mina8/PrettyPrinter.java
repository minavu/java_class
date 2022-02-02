package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    public PrettyPrinter(String fileName) throws IOException {
        this.writer = new FileWriter(fileName);
    }

    public PrettyPrinter() {
        this.writer = null;
    }

    @Override
    public void dump(Airline airline) {
        System.out.println("Printing all flight information for " + airline.getName() + " Airline.");
        System.out.println("Flights ordered by departing airport and departure date/time.\n");
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
            System.out.printf("%-4s->%4s %8s  %d\n", src, dest, "Flight", flightNumber);
            System.out.printf("%10s %8s  %s at %s\n", "", "Depart", srcName, departure);
            System.out.printf("%10s %8s  %s at %s\n", "", "Arrive", destName, arrival);
            System.out.printf("%10s %8s  %s\n\n", "", "Duration", duration);
        });
    }

    public void dump(Airline airline, String fileName) {
        try (PrintWriter pw = new PrintWriter(this.writer)) {
            pw.println("Printing all flight information for " + airline.getName() + " Airline.");
            pw.println("Flights ordered by departing airport and departure date/time.\n");
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
                pw.printf("%-4s->%4s %8s  %d\n", src, dest, "Flight", flightNumber);
                pw.printf("%10s %8s  %s at %s\n", "", "Depart", srcName, departure);
                pw.printf("%10s %8s  %s at %s\n", "", "Arrive", destName, arrival);
                pw.printf("%10s %8s  %s\n\n", "", "Duration", duration);
            });
            pw.flush();
        }
    }
}
