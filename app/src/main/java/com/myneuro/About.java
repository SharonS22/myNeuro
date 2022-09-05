package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {
    DBHelper_MyNeuro DB;
    TextView txtabout, whatsnew, version, txtaim, aimdet, txtcontact, contactdet, txtclose;
    ImageView closeimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        whatsnew = (TextView) findViewById(R.id.whatsnew);
        version = (TextView) findViewById(R.id.version);
        txtaim = (TextView) findViewById(R.id.txtaim);
        txtabout = (TextView) findViewById(R.id.txtAbout);
        aimdet = (TextView) findViewById(R.id.aimdet);
        txtcontact = (TextView) findViewById(R.id.txtcontact);
        contactdet = (TextView) findViewById(R.id.contactdet);
        txtclose = (TextView) findViewById(R.id.txtclose);
        closeimg = (ImageView) findViewById(R.id.closeimg);
        DB = new DBHelper_MyNeuro(this);

        //navigation
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

        //close tab button
        closeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}