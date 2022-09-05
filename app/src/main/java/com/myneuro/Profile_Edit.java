package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile_Edit extends AppCompatActivity {

    EditText txtname, txtdob, txtchi, txtgp, txtusername;
    Button btnsave;
    long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        id = getIntent().getExtras().getLong("id");
        String username = getIntent().getExtras().getString("username");
        String name = getIntent().getExtras().getString("name");
        String dob = getIntent().getExtras().getString("dob");
        String chi = getIntent().getExtras().getString("chi");
        String gp = getIntent().getExtras().getString("gp");

        txtusername = findViewById(R.id.txtusername1);
        txtdob = findViewById(R.id.txtdob1);
        txtname = findViewById(R.id.txtname1);
        txtchi = findViewById(R.id.txtchi);
        txtgp = findViewById(R.id.txtgp);
        btnsave = findViewById(R.id.btnsave);

        txtusername.setText(username);
        txtname.setText(name);
        txtdob.setText(dob);
        txtchi.setText(chi);
        txtgp.setText(gp);

        //Navigation Bar
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

    //Update profile
    public void editProfile(View view) {
        String username = txtusername.getText().toString();
        String name = txtname.getText().toString();
        String dob = txtdob.getText().toString();
        String chi = txtchi.getText().toString();
        String gp = txtgp.getText().toString();

        Profile_view.DB.updateProfile(id, username, name, dob, chi, gp);
        startActivity(new Intent(com.myneuro.Profile_Edit.this, Profile_view.class));
        finish();
    }
}
