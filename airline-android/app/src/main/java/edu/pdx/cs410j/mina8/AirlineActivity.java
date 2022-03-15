package edu.pdx.cs410j.mina8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.TreeSet;

public class AirlineActivity extends AppCompatActivity {

    private static final int NEW_FLIGHT_REQUEST_CODE = 1;
    private final Collection<Airline> hub = new TreeSet<>();

    private ArrayAdapter<Airline> airlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);

        this.airlines = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        ListView listView = findViewById(R.id.airlinesList);
        listView.setAdapter(this.airlines);

        this.airlines.add(new Airline("abc"));
        this.airlines.add(new Airline("def"));
        this.airlines.add(new Airline("ghi"));
        this.airlines.add(new Airline("jkl"));
        this.airlines.add(new Airline("mno"));
        this.airlines.add(new Airline("pqr"));
        this.airlines.add(new Airline("stu"));
        this.airlines.add(new Airline("vwx"));
        this.airlines.add(new Airline("yzz"));
        this.airlines.add(new Airline("zzz"));

        Intent data = getIntent();
        if (data.hasExtra(MainActivity.AIRLINES_HUB)) {
            TreeSet<Airline> hub = (TreeSet<Airline>) data.getSerializableExtra(MainActivity.AIRLINES_HUB);
            for (Airline a : hub) {
                this.airlines.add(a);
            }
        }
    }

//    public void newFlightBtnHandler(View view) {
//        Intent intent = new Intent(this, newFlightActivity.class);
//        startActivityForResult(intent, NEW_FLIGHT_REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == NEW_FLIGHT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            Airline airline = (Airline) data.getSerializableExtra(newFlightActivity.NEW_AIRLINE);
//            Flight flight = (Flight) data.getSerializableExtra(newFlightActivity.NEW_FLIGHT);
//
//            if (!hub.contains(airline)) {
//                airline.addFlight(flight);
//                hub.add(airline);
//            } else {
//                for (Airline a : hub) {
//                    if (a.getName().equalsIgnoreCase(airline.getName())) {
//                        a.addFlight(flight);
//                        break;
//                    }
//                }
//            }
//
////            writeHubToDisk();
////            Toast.makeText(this, flight.toString(), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "Size of hub is " + String.valueOf(hub.size()), Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "A new flight was not created!", Toast.LENGTH_LONG).show();
//        }
//    }

//    private void writeHubToDisk() {
//        File file = new File(this.getFilesDir(), "sums.txt");
//        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
//            pw.println("testing write to disk");
//            Toast.makeText(this, "File path is " + String.valueOf(file), Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
////        File file = new File(this.getFilesDir(), "hub.txt");
////        try {
////            TextDumper textDumper = new TextDumper(new FileWriter(file));
////            for (Airline a : hub) {
////                textDumper.dump(a);
////            }
////        } catch (IOException e) {
////            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
////        }
//    }
}