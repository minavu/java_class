package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void calculateSum(View view) {
        EditText leftOperand = findViewById(R.id.leftOperand);
        EditText rightOperand = findViewById(R.id.rightOperand);

        String left = leftOperand.getText().toString();
        String right = rightOperand.getText().toString();

        int sum = Integer.parseInt(left) + Integer.parseInt(right);

        EditText sumResult = findViewById(R.id.sumResult);
        sumResult.setText(String.valueOf(sum));
    }
}