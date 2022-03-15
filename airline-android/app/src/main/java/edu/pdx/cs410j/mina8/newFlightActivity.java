package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class newFlightActivity extends AppCompatActivity {

    protected static final String NEW_FLIGHT = "New Flight";
    protected static final String NEW_AIRLINE = "New Airline";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);
    }

    public void createNewFlightBtnHandler(View view) {
        EditText airlineName = findViewById(R.id.airlineName);
        EditText flightNumber = findViewById(R.id.flightNumber);
        EditText srcCode = findViewById(R.id.srcCode);
        EditText departDate = findViewById(R.id.departDate);
        EditText departTime = findViewById(R.id.departTime);
        RadioGroup departRadio = findViewById(R.id.departAMPM);
        int departRadioSelectedID = departRadio.getCheckedRadioButtonId();
        RadioButton departAMPM = findViewById(departRadioSelectedID);
        EditText destCode = findViewById(R.id.destCode);
        EditText arriveDate = findViewById(R.id.arriveDate);
        EditText arriveTime = findViewById(R.id.arriveTime);
        RadioGroup arriveRadio = findViewById(R.id.arriveAMPM);
        int arriveRadioSelectedID = arriveRadio.getCheckedRadioButtonId();
        RadioButton arriveAMPM = findViewById(arriveRadioSelectedID);


        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(
                airlineName.getText().toString(),
                flightNumber.getText().toString(),
                srcCode.getText().toString(),
                departDate.getText().toString(),
                departTime.getText().toString(),
                departAMPM.getText().toString(),
                destCode.getText().toString(),
                arriveDate.getText().toString(),
                arriveTime.getText().toString(),
                arriveAMPM.getText().toString()
        ));

        try {
            Airline airline = new Airline(airlineName.getText().toString());
            Flight flight = new Flight(argsList);
            Intent data = new Intent();
            data.putExtra(NEW_AIRLINE, airline);
            data.putExtra(NEW_FLIGHT, flight);
            setResult(RESULT_OK, data);
            finish();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void exitBtnHandler(View view) {
        finish();
    }
}