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
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Rem_view extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvreminder;
    FloatingActionButton btnfloating;
    static DBHelper_MyNeuro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_view);
        DB = new DBHelper_MyNeuro(this);
        toolbar = findViewById(R.id.toolbar);
        lvreminder = findViewById(R.id.lvreminder);
        btnfloating = findViewById(R.id.btnfloating);
        SimpleCursorAdapter simpleCursorAdapter = DB.populateReminderFromDB();
        lvreminder.setAdapter(simpleCursorAdapter);
        lvreminder.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String title = cursor.getString(1);
                String details = cursor.getString(2);
                String time = cursor.getString(3);
                String repeat = cursor.getString(4);
                String interval = cursor.getString(5);
                String type = cursor.getString(6);

                Intent intent = new Intent(com.myneuro.Rem_view.this, Rem_edit.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("detail", details);
                intent.putExtra("time", time);
                intent.putExtra("repeat", repeat);
                intent.putExtra("interval", interval);
                intent.putExtra("type", type);

                startActivity(intent);
                finish();
                Toast.makeText(com.myneuro.Rem_view.this, "Click to edit reminder", Toast.LENGTH_LONG).show();
            }
        });

        btnfloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Rem_add.class);
                startActivity(intent);

            }
        });


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

}