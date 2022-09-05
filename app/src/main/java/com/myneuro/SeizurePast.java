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

public class SeizurePast extends AppCompatActivity {
    static DBHelper_MyNeuro DB;
    ListView lvseizure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seizure_past);
        DB = new DBHelper_MyNeuro(this);
        lvseizure = findViewById(R.id.lvseizure);
        SimpleCursorAdapter simpleCursorAdapter = DB.populateSeizureFromDB();
        lvseizure.setAdapter(simpleCursorAdapter);
        lvseizure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String location = cursor.getString(3);
                String type = cursor.getString(4);
                String duration = cursor.getString(5);
                String awareness = cursor.getString(6);
                String facialexp = cursor.getString(7);
                String headmov = cursor.getString(8);
                String jerking = cursor.getString(9);
                String fall = cursor.getString(10);
                String aftereffect = cursor.getString(11);
                String injury = cursor.getString(12);

                Intent intent = new Intent(com.myneuro.SeizurePast.this, seizure_view.class);
                intent.putExtra("id", id);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("location", location);
                intent.putExtra("type", type);
                intent.putExtra("duration", duration);
                intent.putExtra("awareness", awareness);
                intent.putExtra("facialexp", facialexp);
                intent.putExtra("headmov", headmov);
                intent.putExtra("jerking", jerking);
                intent.putExtra("fall", fall);
                intent.putExtra("aftereffect", aftereffect);
                intent.putExtra("injury", injury);

                startActivity(intent);
                finish();
                Toast.makeText(com.myneuro.SeizurePast.this, "Click to view seizure details", Toast.LENGTH_LONG).show();
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

    }
}