package edu.pdx.cs410j.mina8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;
import java.util.TreeSet;

public class AirlineActivity extends AppCompatActivity {

    private static final int NEW_FLIGHT_REQUEST_CODE = 1;
    private final Collection<Airline> hub = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);
    }

    public void newFlightBtnHandler(View view) {
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

//            Toast.makeText(this, flight.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Size of hub is " + String.valueOf(hub.size()), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "A new flight was not created!", Toast.LENGTH_LONG).show();
        }
    }
}