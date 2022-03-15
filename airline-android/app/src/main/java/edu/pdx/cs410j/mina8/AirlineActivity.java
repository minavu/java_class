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
    private ArrayAdapter<Airline> airlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);

        this.airlines = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        ListView listView = findViewById(R.id.airlinesList);
        listView.setAdapter(this.airlines);

        Intent data = getIntent();
        if (data.hasExtra(MainActivity.AIRLINES_HUB)) {
            TreeSet<Airline> hub = (TreeSet<Airline>) data.getSerializableExtra(MainActivity.AIRLINES_HUB);
            for (Airline a : hub) {
                this.airlines.add(a);
            }
        }
    }
}