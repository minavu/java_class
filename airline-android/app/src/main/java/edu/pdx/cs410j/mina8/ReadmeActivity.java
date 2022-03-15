package edu.pdx.cs410j.mina8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        TextView readmeDisplay = findViewById(R.id.readmeDisplay);

        InputStream inputStream = this.getResources().openRawResource(R.raw.readme);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            Toast.makeText(ReadmeActivity.this, "Cannot read README file", Toast.LENGTH_LONG).show();
        }

        readmeDisplay.setText(stringBuilder);
    }

    public void closeActivity(View view) {
        finish();
    }
}