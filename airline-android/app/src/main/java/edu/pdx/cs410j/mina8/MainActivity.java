package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchCalculator(View view) {
        Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void displayReadmeActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ReadmeActivity.class);
        startActivity(intent);
    }

    public void displayAirlineActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AirlineActivity.class);
        startActivity(intent);
    }
}