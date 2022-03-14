package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void calculateSum(View view) {
        EditText leftOperand = findViewById(R.id.leftOperand);
        EditText rightOperand = findViewById(R.id.rightOperand);

        int left, right;
        try {
            left = Integer.parseInt(leftOperand.getText().toString());
            right = Integer.parseInt(rightOperand.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(CalculatorActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        int sum = left + right;

        EditText sumResult = findViewById(R.id.sumResult);
        sumResult.setText(String.valueOf(sum));
    }
}