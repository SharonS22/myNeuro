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

public class seizure_view extends AppCompatActivity {
    TextView item_date1, item_time1, item_location1, item_type1, item_duration1, item_awareness1, item_facialexp1, item_headmov1, item_jerking1,
            item_fall1, item_aftereffect1, item_injury1, delete;
    Button btnok;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seizure_edit);

        id = getIntent().getExtras().getLong("id");
        String date = getIntent().getExtras().getString("date");
        String time = getIntent().getExtras().getString("time");
        String location = getIntent().getExtras().getString("location");
        String type = getIntent().getExtras().getString("type");
        String duration = getIntent().getExtras().getString("duration");
        String awareness = getIntent().getExtras().getString("awareness");
        String facialexp = getIntent().getExtras().getString("facialexp");
        String headmov = getIntent().getExtras().getString("headmov");
        String jerking = getIntent().getExtras().getString("jerking");
        String fall = getIntent().getExtras().getString("fall");
        String aftereffect = getIntent().getExtras().getString("aftereffect");
        String injury = getIntent().getExtras().getString("injury");

        item_date1 = findViewById(R.id.item_date1);
        item_time1 = findViewById(R.id.item_time1);
        item_location1 = findViewById(R.id.item_location1);
        item_type1 = findViewById(R.id.item_type1);
        item_duration1 = findViewById(R.id.item_duration1);
        item_awareness1 = findViewById(R.id.item_awareness1);
        item_facialexp1 = findViewById(R.id.item_facialexp1);
        item_headmov1 = findViewById(R.id.item_headmov1);
        item_jerking1 = findViewById(R.id.item_jerking1);
        item_fall1 = findViewById(R.id.item_fall1);
        item_aftereffect1 = findViewById(R.id.item_aftereffect1);
        item_injury1 = findViewById(R.id.item_injury1);
        btnok = findViewById(R.id.btnok);
        delete = findViewById(R.id.delete);

        item_date1.setText(date);
        item_time1.setText(time);
        item_location1.setText(location);
        item_type1.setText(type);
        item_duration1.setText(duration);
        item_awareness1.setText(awareness);
        item_facialexp1.setText(facialexp);
        item_headmov1.setText(headmov);
        item_jerking1.setText(jerking);
        item_fall1.setText(fall);
        item_aftereffect1.setText(aftereffect);
        item_injury1.setText(injury);

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

        //Ok button to go back to previous page
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeizurePast.class);
                startActivity(intent);

            }
        });
    }

    //Delete added seizure entry
    public void deleteSeizure(View view) {
        SeizurePast.DB.deleteSeizure(id);
        startActivity(new Intent(com.myneuro.seizure_view.this, SeizurePast.class));
        finish();
    }
}