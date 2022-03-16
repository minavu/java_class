package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.pdx.cs410J.AirportNames;

public class SearchActivity extends AppCompatActivity {
    private TreeSet<Airline> hub;
    private ArrayAdapter<String> airlines;
    private ArrayAdapter<String> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.airlineSpinner);

        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);

        this.airlines = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.airlines.add("---Select an airline---");
        this.airlines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(this.airlines);

        this.flights = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.flightsList);
        listView.setAdapter(this.flights);

        Intent data = getIntent();
        if (data.hasExtra(MainActivity.AIRLINES_HUB)) {
            hub = (TreeSet<Airline>) data.getSerializableExtra(MainActivity.AIRLINES_HUB);
            for (Airline a : hub) {
                this.airlines.add(a.getName());
                a.getFlights().forEach(flight -> {
                    this.flights.add(prettyPrintFlight(flight).toString());
                });
            }
        }
//        if (data.hasExtra(MainActivity.AIRLINE_SEARCH)) {
//            Airline airline = (Airline) data.getSerializableExtra(MainActivity.AIRLINE_SEARCH);
//            airline.getFlights().forEach(flight -> {
//                flights.add(flight);
//            });
//        }
    }

    public StringBuilder prettyPrintFlight(Flight flight) {
        String airline = flight.getAirline();
        String flightNumber = String.valueOf(flight.getNumber());
        String src = flight.getSource();
        String srcName = AirportNames.getName(flight.getSource());
        String departure = flight.getDepartureString();
        String dest = flight.getDestination();
        String destName = AirportNames.getName(flight.getDestination());
        String arrival = flight.getArrivalString();
        String duration = flight.getFlightDuration();

        String EMPTY_STRING = "";
        StringBuilder stringBuilder = new StringBuilder("\n");
        stringBuilder.append(String.format("%s Airline\n", airline));
        stringBuilder.append(String.format("%10s%-4s->%4s\n", EMPTY_STRING, src, dest));
        stringBuilder.append(String.format("%10s%8s%s\n", EMPTY_STRING, "Flight", flightNumber));
        stringBuilder.append(String.format("%10s%8s%s at %s\n", EMPTY_STRING, "Depart", srcName, departure));
        stringBuilder.append(String.format("%10s%8s%s at %s\n", EMPTY_STRING, "Arrive", destName, arrival));
        stringBuilder.append(String.format("%10s%8s%s\n", EMPTY_STRING, "Duration", duration));

        return stringBuilder;
    }
}