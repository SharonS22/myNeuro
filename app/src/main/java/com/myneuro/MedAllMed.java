package com.myneuro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedAllMed extends AppCompatActivity {
    static DBHelper_MyNeuro DB;
    ListView lvmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_all_med);

        DB = new DBHelper_MyNeuro(this);
        lvmed = findViewById(R.id.lvmed);
        SimpleCursorAdapter simpleCursorAdapter = DB.populateListViewFromDB();
        lvmed.setAdapter(simpleCursorAdapter);
        lvmed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String name = cursor.getString(1);
                String dosage = cursor.getString(2);
                String frequency = cursor.getString(3);
                String duration = cursor.getString(4);
                String start_date = cursor.getString(5);

                Intent intent = new Intent(com.myneuro.MedAllMed.this, Med_Edit.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("dosage", dosage);
                intent.putExtra("frequency", frequency);
                intent.putExtra("duration", duration);
                intent.putExtra("start_date", start_date);
                startActivity(intent);
                finish();
                Toast.makeText(com.myneuro.MedAllMed.this, "Click to edit the details", Toast.LENGTH_LONG).show();
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
}