package com.example.medicine_20;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText medname, meddate;
    Button insert, fetch;
    Spinner day;
    Switch switch1;
    TextView medtxt;
    DataBaseConn dbconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medname = findViewById(R.id.medicinename);
        meddate = findViewById(R.id.date);
        insert = findViewById(R.id.insert);
        fetch = findViewById(R.id.fetch);
        day = findViewById(R.id.spinner);
        switch1 = findViewById(R.id.switch1);
        medtxt = findViewById(R.id.medtext);
        dbconnection = new DataBaseConn(this);

        fetch.setVisibility(View.INVISIBLE);

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            fetch.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            insert.setVisibility(isChecked ? View.INVISIBLE : View.VISIBLE);
            medname.setVisibility(isChecked ? View.INVISIBLE : View.VISIBLE);
            medtxt.setVisibility(isChecked ? View.INVISIBLE : View.VISIBLE);
        });

        insert.setOnClickListener(v -> {
            String name = medname.getText().toString();
            String date = meddate.getText().toString();
            String time = day.getSelectedItem().toString();
            boolean isInserted = dbconnection.insertvalues(name, date, time);
            showToast(isInserted ? "Data Inserted" : "Data Not Inserted");
            medname.setText("");
            meddate.setText("");
        });

        fetch.setOnClickListener(v -> {
            String date = meddate.getText().toString();
            String time = day.getSelectedItem().toString();
            Cursor c = dbconnection.FetchData(date, time);
            if (c != null) {
                StringBuilder med = new StringBuilder();
                int medicineNameIndex = c.getColumnIndex("MedicineName");
                if (medicineNameIndex >= 0) {
                    if (c.moveToFirst()) {
                        do {
                            med.append(c.getString(medicineNameIndex)).append("\n");
                        } while (c.moveToNext());
                        showToast(med.toString());
                    } else {
                        showToast("No Entries in Database");
                    }
                } else {
                    showToast("Column 'MedicineName' not found");
                }
                c.close();
            } else {
                showToast("Cursor is null");
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}