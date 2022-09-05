package com.myneuro;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class seizure_add extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerawareness, spinnerfacialexp, spinnerheadmov, spinnertypelist, spinnertimemm, spinnertimess;;
    Button btnsaveentry;
    EditText etdate, ettime, etlocation;
    ImageView calendar, time;
    SwitchCompat swtjerking, swtfall, swtinjury;
    CheckBox aware, confused, tired;
    TextView txtaftereffect;
    DBHelper_MyNeuro DB;
    private int mDate, mMonth, mYear;
    int t1Hour, t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seizure_add);
        etdate = findViewById(R.id.etdate);
        calendar = findViewById(R.id.calendar);
        ettime = findViewById(R.id.ettime);
        etlocation = findViewById(R.id.etlocation);
        time = findViewById(R.id.time);
        spinnertypelist = findViewById(R.id.typelist);
        spinnertimemm = findViewById(R.id.timemm);
        spinnertimess = findViewById(R.id.timess);
        btnsaveentry = findViewById(R.id.btnsaveentry);
        spinnerawareness = findViewById(R.id.awareness);
        spinnerfacialexp = findViewById(R.id.facialexp);
        spinnerheadmov = findViewById(R.id.headmov);
        swtjerking = findViewById(R.id.swtjerking);
        swtfall = findViewById(R.id.swtfall);
        txtaftereffect = findViewById(R.id.txtaftereffect);
        swtinjury = findViewById(R.id.swtinjury);
        aware = findViewById(R.id.aware);
        confused = findViewById(R.id.confused);
        tired = findViewById(R.id.tired);
        DB = new DBHelper_MyNeuro(this);


        //Seizure Type Dropdown Lists
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(
                this, R.array.Add_Seizure_Type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypelist.setAdapter(adapterType);
        spinnertypelist.setOnItemSelectedListener(this);

        //Seizure Duration Dropdown Lists
        ArrayAdapter<CharSequence> adapterDurationmm = ArrayAdapter.createFromResource(
                this, R.array.Add_Duration_mm, android.R.layout.simple_spinner_item);
        adapterDurationmm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertimemm.setAdapter(adapterDurationmm);
        spinnertimemm.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDurationss = ArrayAdapter.createFromResource(
                this, R.array.Add_Duration_ss, android.R.layout.simple_spinner_item);
        adapterDurationss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertimess.setAdapter(adapterDurationss);
        spinnertimess.setOnItemSelectedListener(this);

        //date picker
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH)+1;
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datepicker = new DatePickerDialog(com.myneuro.seizure_add.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        etdate.setText(date + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDate);
                datepicker.show();
            }
        });

        //time picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(
                        com.myneuro.seizure_add.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour = hourOfDay;
                                t1Minute = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t1Hour, t1Minute);
                                ettime.setText(DateFormat.format("hh:mm:aa", calendar));
                            }
                        }, 12, 0, false);
                timePicker.updateTime(t1Hour, t1Minute);
                timePicker.show();
            }
        });

        //Before Seizure Dropdown Lists
        ArrayAdapter<CharSequence> adapterAwareness = ArrayAdapter.createFromResource(
                this, R.array.Awareness, android.R.layout.simple_spinner_item);
        adapterAwareness.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerawareness.setAdapter(adapterAwareness);
        spinnerawareness.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterfacialexp = ArrayAdapter.createFromResource(
                this, R.array.facialexp, android.R.layout.simple_spinner_item);
        adapterfacialexp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfacialexp.setAdapter(adapterfacialexp);
        spinnerfacialexp.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterheadmov = ArrayAdapter.createFromResource(
                this, R.array.headmov, android.R.layout.simple_spinner_item);
        adapterheadmov.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerheadmov.setAdapter(adapterheadmov);
        spinnerheadmov.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //During Seizure Switch

        SharedPreferences sharedPreferences3 = getSharedPreferences("save", MODE_PRIVATE);
        swtfall.setChecked(sharedPreferences3.getBoolean("No", false));

        swtfall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtfall.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("Yes", true);
                    editor.apply();
                    swtfall.setChecked(true);
                } else{
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("No", false);
                    editor.apply();
                    swtfall.setChecked(false);
                }
            }
        });

        SharedPreferences sharedPreferences4 = getSharedPreferences("save", MODE_PRIVATE);
        swtjerking.setChecked(sharedPreferences4.getBoolean("No", false));

        swtjerking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtjerking.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("Yes", true);
                    editor.apply();
                    swtjerking.setChecked(true);
                } else{
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("No", false);
                    editor.apply();
                    swtjerking.setChecked(false);
                }
            }
        });

        SharedPreferences sharedPreferences2 = getSharedPreferences("save", MODE_PRIVATE);
        swtinjury.setChecked(sharedPreferences2.getBoolean("No", false));

        swtinjury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtinjury.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("Yes", true);
                    editor.apply();
                    swtinjury.setChecked(true);
                } else{
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("No", false);
                    editor.apply();
                    swtinjury.setChecked(false);
                }
            }
        });


        //Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_seizure);

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

        // save button
        btnsaveentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = etdate.getText().toString();
                String time = ettime.getText().toString();
                String location = etlocation.getText().toString();
                String type = spinnertypelist.getSelectedItem().toString();
                int mm = Integer.parseInt(spinnertimemm.getSelectedItem().toString());
                int ss = Integer.parseInt(spinnertimess.getSelectedItem().toString());
                String Duration = mm + ":" + ss;
                String awareness = spinnerawareness.getSelectedItem().toString();
                String facialexp = spinnerfacialexp.getSelectedItem().toString();
                String headmov = spinnerheadmov.getSelectedItem().toString();
                //Retrieving jerking switch status
                StringBuilder Jerking = new StringBuilder();
                Jerking.append(" ");
                if (swtjerking.isChecked()){
                    Jerking.append(swtjerking.getTextOn().toString());
                } else{
                    Jerking.append(swtjerking.getTextOff().toString());
                }
                String jerking =  Jerking.toString();
                //Retrieving fall switch status
                StringBuilder Fall = new StringBuilder();
                Fall.append(" ");
                if (swtfall.isChecked()){
                    Fall.append(swtfall.getTextOn().toString());
                } else{
                    Fall.append(swtfall.getTextOff().toString());
                }
                String fall =  Fall.toString();
                //Retrieving after effect checked boxes value
                StringBuilder afterEffect = new StringBuilder();
                afterEffect.append(" ");
                if (aware.isChecked()){
                    afterEffect.append(aware.getText().toString());
                }
                if (confused.isChecked()){
                    afterEffect.append(confused.getText().toString());
                }
                if (tired.isChecked()){
                    afterEffect.append(tired.getText().toString());
                }
                String aftereffect =  afterEffect.toString();
                //Retrieving injury switch status
                StringBuilder Injury = new StringBuilder();
                Injury.append(" ");
                if (swtinjury.isChecked()){
                    Injury.append(swtinjury.getTextOn().toString());
                } else{
                    Injury.append(swtinjury.getTextOff().toString());
                }
                String injury=  Injury.toString();

                if (date.equals("") || time.equals("") || location.equals("") || Duration.equals(""))
                    Toast.makeText(com.myneuro.seizure_add.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
                Boolean addSeizureData = DB.addSeizure(date, time, location, type, Duration, awareness, facialexp, headmov, jerking, fall, aftereffect,injury);

                if (addSeizureData == true) {
                    Intent intent = new Intent(getApplicationContext(), Seizure.class);
                    startActivity(intent);
                    Toast.makeText(com.myneuro.seizure_add.this, "Data Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(com.myneuro.seizure_add.this, "Don't leave fields blank, add unknown if not known", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}