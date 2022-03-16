package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.TreeSet;

import edu.pdx.cs410J.AirportNames;

public class SearchActivity extends AppCompatActivity implements OnItemSelectedListener {
    private TreeSet<Airline> hub;
    private ArrayAdapter<String> airlines;
    private ArrayAdapter<String> sources;
    private ArrayAdapter<String> destinations;
    private ArrayAdapter<String> flights;

    Airline airline = null;
    String source = null;
    String destination = null;

    Spinner airlineSpinner = null;
    Spinner sourceSpinner = null;
    Spinner destSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        airlineSpinner = findViewById(R.id.airlineSpinner);
        this.airlines = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.airlines.add("---All airlines---");
        this.airlines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airlineSpinner.setAdapter(this.airlines);
        // Spinner click listener
        airlineSpinner.setOnItemSelectedListener(this);

        sourceSpinner = findViewById(R.id.sourceSpinner);
        this.sources = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.sources.add("---All sources---");
        this.sources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(this.sources);
        // Spinner click listener
        sourceSpinner.setOnItemSelectedListener(this);

        destSpinner = findViewById(R.id.destSpinner);
        this.destinations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.destinations.add("---All destinations---");
        this.destinations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destSpinner.setAdapter(this.destinations);
        // Spinner click listener
        destSpinner.setOnItemSelectedListener(this);



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
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(String.format("%s Airline\n\n", airline));
        stringBuilder.append(String.format("%10s%-4s->%4s\n", EMPTY_STRING, src, dest));
        stringBuilder.append(String.format("%10s%-10s%s\n", EMPTY_STRING, "Flight", flightNumber));
        stringBuilder.append(String.format("%10s%-10s%s at %s\n", EMPTY_STRING, "Depart", srcName, departure));
        stringBuilder.append(String.format("%10s%-10s%s at %s\n", EMPTY_STRING, "Arrive", destName, arrival));
        stringBuilder.append(String.format("%10s%-10s%s\n", EMPTY_STRING, "Duration", duration));

        return stringBuilder;
    }

    public void clearBtnHandler(View view) {
        airlineSpinner.setSelection(0);

        this.sources.clear();
        this.sources.add("---All sources---");
        sourceSpinner.setSelection(0);

        this.destinations.clear();
        this.destinations.add("---All destinations---");
        destSpinner.setSelection(0);
    }

    public void searchBtnHandler(View view) {
        Toast.makeText(this, "SEARCH! " + airline.getName() + ", " + source + ", " + destination, Toast.LENGTH_LONG).show();

        this.flights.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
        String selected = parent.getSelectedItem().toString();
        if (selected.contains("All")) {
            return;
        }
        switch (parent.getId()) {

            case R.id.airlineSpinner:
                clearBtnHandler(view);
                airlineSpinner.setSelection(index);
                for (Airline a : hub) {
                    if (a.getName().equalsIgnoreCase(selected)) {
                        airline = a;
                        airline.getFlights().forEach(flight -> {
                            this.sources.add(flight.getSource());
                        });
                        break;
                    }
                }
                break;


            case R.id.sourceSpinner:
                this.destinations.clear();
                this.destinations.add("---All destinations---");
                destSpinner.setSelection(0);
                source = selected;
                airline.getFlights().forEach(flight -> {
                    if (flight.getSource().equalsIgnoreCase(selected)) {
                        this.destinations.add(flight.getDestination());
                    }
                });
                break;


            case R.id.destSpinner:
                destination = selected;
                break;


            default:
                Toast.makeText(this, selected + " was sent to default branch of switch statement", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}