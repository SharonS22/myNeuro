package com.myneuro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MedNewEntry extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnadd;
    EditText etname, etdose, etduration, etdate;
    TextView txtname, txtfrequency, txtdose, txtduration, txtstartdate, txtinfo;
    ImageView calendar, info;
    Spinner spinnerfrequency;
    DBHelper_MyNeuro DB;
    private int mDate, mMonth, mYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_new_entry);

        btnadd = findViewById(R.id.btnadd);
        etname = findViewById(R.id.etname);
        etdose = findViewById(R.id.etdose);
        etduration = findViewById(R.id.etduration);
        txtinfo = findViewById(R.id.txtinfo);
        info = findViewById(R.id.info);
        etdate = findViewById(R.id.etdate);
        txtname = findViewById(R.id.txtname);
        txtfrequency = findViewById(R.id.txtfrequency);
        spinnerfrequency = findViewById(R.id.freq);
        txtdose = findViewById(R.id.txtdose);
        txtduration = findViewById(R.id.txtduration);
        txtstartdate = findViewById(R.id.txtstartdate);
        calendar = findViewById(R.id.calendar);
        DB = new DBHelper_MyNeuro(this);

        //Frequency Dropdown Lists
        ArrayAdapter<CharSequence> adapterFrequency = ArrayAdapter.createFromResource(
                this, R.array.freq, android.R.layout.simple_spinner_item);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrequency.setAdapter(adapterFrequency);
        spinnerfrequency.setOnItemSelectedListener(this);

        //Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_medication);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_seizure:
                        startActivity(new Intent(getApplicationContext(), Seizure.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_medication:
                        startActivity(new Intent(getApplicationContext(), Medication.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_reminder:
                        startActivity(new Intent(getApplicationContext(), Reminder.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        //info button for duration
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(com.myneuro.MedNewEntry.this, "Enter INDEFINITE if duration is not definite or UNKNOWN if not known", Toast.LENGTH_SHORT).show();
            }
        });

        //Calendar option for choosing date
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH)+1;
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog(com.myneuro.MedNewEntry.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        etdate.setText(date + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                datepicker.show();
            }
        });

        //Add new medication
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medName = etname.getText().toString();
                String dose = etdose.getText().toString();
                String date = etdate.getText().toString();
                String frequency = spinnerfrequency.getSelectedItem().toString();
                String Duration = etduration.getText().toString();

                if (medName.equals("") || dose.equals("") || date.equals("") || frequency.equals("") || Duration.equals(""))
                    Toast.makeText(com.myneuro.MedNewEntry.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
                Boolean addMedData = DB.addMedication(medName, frequency, dose, Duration, date);

                if (addMedData == true) {
                    Intent intent = new Intent(getApplicationContext(), Medication.class);
                    startActivity(intent);
                    Toast.makeText(com.myneuro.MedNewEntry.this, "Data Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(com.myneuro.MedNewEntry.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}