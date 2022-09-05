package com.myneuro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Calendar;

public class Rem_add extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    EditText title, details, time;
    Spinner rep_interval, rep_type;
    TextView repeat, txtinfo;
    Switch swtrepeat;
    Button btnsave, btncancel;
    ImageView info;
    DBHelper_MyNeuro DB;
    private int interval;
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_add);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        time = findViewById(R.id.time);
        rep_interval = findViewById(R.id.rep_interval);
        rep_type = findViewById(R.id.rep_type);
        swtrepeat = findViewById(R.id.swtrepeat);
        btncancel = findViewById(R.id.btncancel);
        btnsave = findViewById(R.id.btnsave);
        repeat = findViewById(R.id.repeat);
        txtinfo = findViewById(R.id.txtinfo);
        info = findViewById(R.id.info);
        DB = new DBHelper_MyNeuro(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_reminder);

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
                        return true;
                }
                return false;
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        ArrayAdapter<CharSequence> adapterInterval = ArrayAdapter.createFromResource(
                this, R.array.interval, android.R.layout.simple_spinner_item);
        adapterInterval.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rep_interval.setAdapter(adapterInterval);
        rep_interval.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rep_type.setAdapter(adapterType);
        rep_type.setOnItemSelectedListener(this);

        // save button
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Details = details.getText().toString();
                String Time = time.getText().toString();
                //Retrieving repeat switch status
                StringBuilder repeat = new StringBuilder();
                repeat.append(" ");
                if (swtrepeat.isChecked()) {
                    repeat.append(swtrepeat.getTextOn().toString());
                } else {
                    repeat.append(swtrepeat.getTextOff().toString());
                }
                String Repeat = repeat.toString();
                String interval = rep_interval.getSelectedItem().toString();
                String type = rep_type.getSelectedItem().toString();


                if (Title.equals("") || Time.equals(""))
                    Toast.makeText(com.myneuro.Rem_add.this, "Please Add Title & Time Fields", Toast.LENGTH_SHORT).show();
                Boolean addReminder = DB.addReminder(Title, Details, Time, Repeat, interval, type);

                if (addReminder == true) {
                    Intent intent = new Intent(getApplicationContext(), Reminder.class);
                    startActivity(intent);
                    Toast.makeText(com.myneuro.Rem_add.this, "Reminder Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(com.myneuro.Rem_add.this, "Error", Toast.LENGTH_SHORT).show();

                }

                Calendar c = Calendar.getInstance();
                startAlarm(c);
            }

        });


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
    }

    private void updateTimeText(Calendar c) {
        time.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
    }

    private void startAlarm(Calendar c) {
        String type = rep_type.getSelectedItem().toString();
        Log.i(TAG, type);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Rem_alertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /*if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
            repinterval.equals("1") && rep_type.getSelectedItem()=="Hour"
        }*/
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),(24 * 1000 * 60 * 60),pendingIntent);

       /*if (type.equals("Day")){
           alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                  AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i(TAG, "condition 1");
        }
        else*/ if (type.equals("Hour")){
        interval = Integer.parseInt((String) rep_interval.getSelectedItem());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                (interval*1000*60*60), pendingIntent);
            Log.i(TAG, "condition 2");
        }
        else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            Log.i(TAG, "condition 3");
        }
        /*switch(type){
            case "Day":
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                Log.i(TAG, "condition 1");
                break;

            case "Hour":
                interval = Integer.parseInt((String) rep_interval.getSelectedItem());
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                        (interval*1000*60*60), pendingIntent);
                Log.i(TAG, "condition 2");
                break;

            default:
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                Log.i(TAG, "condition 3");
                break;
        }*/
}
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Rem_alertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(com.myneuro.Rem_add.this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void viewInfo(View view) {
        txtinfo.setText("Select 12 for Twice a Day, 24 for Daily");
    }
}