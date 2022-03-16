package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        airlineSpinner.setOnItemSelectedListener(this);

        sourceSpinner = findViewById(R.id.sourceSpinner);
        this.sources = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.sources.add("---All sources---");
        this.sources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(this.sources);
        sourceSpinner.setOnItemSelectedListener(this);

        destSpinner = findViewById(R.id.destSpinner);
        this.destinations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        this.destinations.add("---All destinations---");
        this.destinations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destSpinner.setAdapter(this.destinations);
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
                    this.flights.add(prettyPrintFlight(flight));
                });
            }
        }

        if (data.hasExtra(MainActivity.AIRLINE_SEARCH)) {
            airline = (Airline) data.getSerializableExtra(MainActivity.AIRLINE_SEARCH);
            airlineSpinner.setSelection(this.airlines.getPosition(airline.getName()));
            searchBtnHandler(null);
        }
    }

    public String prettyPrintFlight(Flight flight) {
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

        return String.format("%s Airline\n\n", airline) +
                String.format("%10s%-4s->%4s\n", EMPTY_STRING, src, dest) +
                String.format("%10s%-10s%s\n", EMPTY_STRING, "Flight", flightNumber) +
                String.format("%10s%-10s%s at %s\n", EMPTY_STRING, "Depart", srcName, departure) +
                String.format("%10s%-10s%s at %s\n", EMPTY_STRING, "Arrive", destName, arrival) +
                String.format("%10s%-10s%s\n", EMPTY_STRING, "Duration", duration);
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
        this.flights.clear();

        if (airline != null && source != null && destination != null) {
            airline.getFlights().forEach(flight -> {
                if (flight.getSource().equalsIgnoreCase(source) && flight.getDestination().equalsIgnoreCase(destination)) {
                    this.flights.add(prettyPrintFlight(flight));
                }
            });
        } else if (airline != null && source != null) {
            airline.getFlights().forEach(flight -> {
                if (flight.getSource().equalsIgnoreCase(source)) {
                    this.flights.add(prettyPrintFlight(flight));
                }
            });
        } else if (airline != null) {
            airline.getFlights().forEach(flight -> {
                this.flights.add(prettyPrintFlight(flight));
            });
        } else {
            for (Airline airline : hub) {
                airline.getFlights().forEach(flight -> {
                    this.flights.add(prettyPrintFlight(flight));
                });
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
        String selected = parent.getSelectedItem().toString();
        if (selected.contains("All") && airline == null) {
            return;
        }

        switch (parent.getId()) {

            case R.id.airlineSpinner:
                clearBtnHandler(view);
                airlineSpinner.setSelection(index);

                if (selected.contains("All")) {
                    airline = null;
                    source = null;
                    destination = null;
                } else {
                    for (Airline a : hub) {
                        if (a.getName().equalsIgnoreCase(selected)) {
                            airline = a;
                            airline.getFlights().forEach(flight -> {
                                if (this.sources.getPosition(flight.getSource()) == -1) {
                                    this.sources.add(flight.getSource());
                                }
                            });
                            break;
                        }
                    }
                }
                break;


            case R.id.sourceSpinner:
                this.destinations.clear();
                this.destinations.add("---All destinations---");
                destSpinner.setSelection(0);

                if (selected.contains("All")) {
                    source = null;
                    destination = null;
                } else {
                    source = selected;
                    airline.getFlights().forEach(flight -> {
                        if (flight.getSource().equalsIgnoreCase(selected) && this.destinations.getPosition(flight.getDestination()) == -1) {
                            this.destinations.add(flight.getDestination());
                        }
                    });
                }
                break;


            case R.id.destSpinner:
                if (selected.contains("All")) {
                    destination = null;
                } else {
                    destination = selected;
                }
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