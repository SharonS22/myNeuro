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

public class Seizure extends AppCompatActivity {
    Button btnAddNewEntry, btnPastEntries;
    TextView seizuretxt;
    DBHelper_MyNeuro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seizure);

        btnAddNewEntry = findViewById(R.id.btnAddNewEntry);
        btnPastEntries = findViewById(R.id.btnPastEntries);
        seizuretxt = findViewById(R.id.seizuretxt);
        DB = new DBHelper_MyNeuro(this);

        //Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_seizure);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_seizure:
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

        //Add new seizure entry button
        btnAddNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), seizure_add.class);
                startActivity(intent);

            }
        });

        //View past seizure entries button
        btnPastEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeizurePast.class);
                startActivity(intent);

            }
        });

    }
}