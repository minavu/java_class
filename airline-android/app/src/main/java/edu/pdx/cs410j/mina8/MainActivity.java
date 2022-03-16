package edu.pdx.cs410j.mina8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    protected static final int NEW_FLIGHT_REQUEST_CODE = 1;
    protected static final String AIRLINE_SEARCH = "Airline Search";
    protected static final String AIRLINES_HUB = "Airlines Hub";
    private final TreeSet<Airline> hub = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataFromDisk();
    }

    public void launchCalculator(View view) {
        Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void helpBtnHandler(View view) {
        Intent intent = new Intent(MainActivity.this, ReadmeActivity.class);
        startActivity(intent);
    }

    public void airlinesBtnHandler(View view) {
        Intent intent = new Intent(MainActivity.this, AirlineActivity.class);
        intent.putExtra(AIRLINES_HUB, hub);
        startActivity(intent);
    }

    public void searchBtnHandler(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(AIRLINES_HUB, hub);
        startActivity(intent);
    }

    public void addFlightBtnHandler(View view) {
        Intent intent = new Intent(this, newFlightActivity.class);
        startActivityForResult(intent, NEW_FLIGHT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FLIGHT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Airline airline = (Airline) data.getSerializableExtra(newFlightActivity.NEW_AIRLINE);
            Flight flight = (Flight) data.getSerializableExtra(newFlightActivity.NEW_FLIGHT);

            if (!hub.contains(airline)) {
                airline.addFlight(flight);
                hub.add(airline);
            } else {
                for (Airline a : hub) {
                    if (a.getName().equalsIgnoreCase(airline.getName())) {
                        a.addFlight(flight);
                        break;
                    }
                }
            }

            writeDataToDisk();
            Toast.makeText(this, "Size of hub is " + String.valueOf(hub.size()), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "A new flight was not created!", Toast.LENGTH_LONG).show();
        }
    }

    private void writeDataToDisk() {
        File file = new File(this.getFilesDir(), "hub.txt");
        try {
            TextDumper textDumper = new TextDumper(new FileWriter(file));
            textDumper.dumpHub(hub);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataFromDisk() {
        File file = new File(this.getFilesDir(), "hub.txt");
        try {
            TextParser textParser = new TextParser(new FileReader(file));
            hub.addAll(textParser.parseHub());
        } catch (IOException | ParserException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}