package com.myneuro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

public class Med_Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txtName, txtdose, txtfrequency, txtduration, txtstartdate, cb, delete;
    EditText etdosage, etduration, etdate;
    Spinner freq;
    ImageView calendar;
    CheckBox cbdiscontinue;
    private int mDate, mMonth, mYear;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_edit);

        id = getIntent().getExtras().getLong("id");
        String name = getIntent().getExtras().getString("name");
        String dosage = getIntent().getExtras().getString("dosage");
        String duration = getIntent().getExtras().getString("duration");
        String start_date = getIntent().getExtras().getString("start_date");

        txtName = findViewById(R.id.txtMedName);
        txtdose = findViewById(R.id.txtdose);
        txtfrequency = findViewById(R.id.txtfrequency);
        txtduration = findViewById(R.id.txtduration);
        txtstartdate = findViewById(R.id.txtstartdate);
        etdosage = findViewById(R.id.etdosage);
        etduration = findViewById(R.id.etduration);
        etdate = findViewById(R.id.etdate);
        freq = findViewById(R.id.freq);
        calendar = findViewById(R.id.calendar);
        cbdiscontinue = findViewById(R.id.cbdiscontinue);
        delete = findViewById(R.id.delete);
        cb = findViewById(R.id.cb);

        txtName.setText(name);
        etdosage.setText(dosage);
        etduration.setText(duration);
        etdate.setText(start_date);

        //Dropdown list for frequency
        ArrayAdapter<CharSequence> adapterFrequency = ArrayAdapter.createFromResource(
                this, R.array.freq, android.R.layout.simple_spinner_item);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freq.setAdapter(adapterFrequency);
        freq.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //Calendar option for choosing date
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog(com.myneuro.Med_Edit.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        etdate.setText(date + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                datepicker.show();
            }
        });

        //Navigation bar
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
    }

    //Update medication
    public void editMedication(View view) {

        if (cbdiscontinue.isChecked())
            cb.setText("Yes");

        String name = txtName.getText().toString();
        String dosage = etdosage.getText().toString();
        String frequency = freq.getSelectedItem().toString();
        String duration = etduration.getText().toString();
        String date = etdate.getText().toString();
        String discontinue = cb.getText().toString();

        if (name.equals("") || dosage.equals("") || frequency.equals("") || frequency.equals("Select") || duration.equals("") || date.equals("")) {
            Toast.makeText(com.myneuro.Med_Edit.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
        } else {
            MedAllMed.DB.updateMedNew(id, name, dosage, frequency, duration, date, discontinue);
            startActivity(new Intent(com.myneuro.Med_Edit.this, MedAllMed.class));
            finish();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Delete inserted medication entry
    public void deleteMed(View view) {
        MedAllMed.DB.deleteMedication(id);
        startActivity(new Intent(com.myneuro.Med_Edit.this, MedAllMed.class));
        finish();
    }
}