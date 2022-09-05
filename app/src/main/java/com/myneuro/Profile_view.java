package com.myneuro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile_view extends AppCompatActivity {
    static DBHelper_MyNeuro DB;
    ListView lvprofile;
    ImageView etimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        DB = new DBHelper_MyNeuro(this);
        lvprofile = findViewById(R.id.lvprofile);
        SimpleCursorAdapter simpleCursorAdapter = DB.profileListViewFromDB();
        lvprofile.setAdapter(simpleCursorAdapter);
        lvprofile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String username = cursor.getString(1);
                String name = cursor.getString(2);
                String dob = cursor.getString(3);
                String chi = cursor.getString(4);
                String gp = cursor.getString(5);

                Intent intent = new Intent(com.myneuro.Profile_view.this, Profile_Edit.class);
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("name", name);
                intent.putExtra("dob", dob);
                intent.putExtra("chi", chi);
                intent.putExtra("gp", gp);
                startActivity(intent);
                finish();

                Toast.makeText(com.myneuro.Profile_view.this, name, Toast.LENGTH_LONG).show();
            }
        });

        //Navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

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
