package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;

import java.io.FileWriter;
import java.io.IOException;
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
        System.out.println("Printing all flight information for airline " + airline.getName() + ".");
        Collection<Flight> flights = airline.getFlights();
        flights.forEach((flight) -> {
            System.out.println("\t" + flight + " with duration " + flight.getFlightDuration());
        });
    }

    public void dump(Airline airline, String fileName) {
        System.out.println("Will be pretty printing airline " + airline.getName() + " and all flights to file.\n");
    }
}
