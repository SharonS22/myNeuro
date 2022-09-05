package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rem_edit extends AppCompatActivity {
    TextView rep_type1, rep_interval1, repeat1, time1, detail1, txtTitle;
    Button btndelete;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_edit);

        id = getIntent().getExtras().getLong("id");
        String title = getIntent().getExtras().getString("title");
        String detail = getIntent().getExtras().getString("detail");
        String time= getIntent().getExtras().getString("time");
        String repeat = getIntent().getExtras().getString("repeat");
        String interval = getIntent().getExtras().getString("interval");
        String type = getIntent().getExtras().getString("type");

        txtTitle = findViewById(R.id.txtTitle);
        detail1 = findViewById(R.id.detail1);
        time1 = findViewById(R.id.time1);
        repeat1 = findViewById(R.id.repeat1);
        rep_interval1 = findViewById(R.id.rep_interval1);
        rep_type1 = findViewById(R.id.rep_type1);
        btndelete = findViewById(R.id.btndelete);

        txtTitle.setText(title);
        detail1.setText(detail);
        time1.setText(time);
        repeat1.setText(repeat);
        rep_interval1.setText(interval);
        rep_type1.setText(type);


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
    }

    public void deleteReminder(View view) {
        Rem_view.DB.deleteReminder(id);
        startActivity(new Intent(com.myneuro.Rem_edit.this, Rem_view.class));
        finish();
    }
}