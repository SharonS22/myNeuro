package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    EditText txtname, txtdob, chi, gp, txtusername;
    Button btnsave, btnupdate;
    String etname, etdob, etchi, etgp, etusername;
    DBHelper_MyNeuro DB;

    private String username, name, dob, CHI, GP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtusername = findViewById(R.id.txtusername);
        txtdob = findViewById(R.id.txtdob);
        txtname = findViewById(R.id.txtname);
        chi = findViewById(R.id.chi);
        gp = findViewById(R.id.gp);
        btnsave = findViewById(R.id.btnsave);
        btnupdate = findViewById(R.id.btnupdate);
        DB = new DBHelper_MyNeuro(this);

        addData();
        /*userProfile();
        getUsername();
        getName();
        getdob();
        getCHI();
        getGp();*/

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

    //Add profile details
    public void addData() {
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etusername = txtusername.getText().toString();
                etname = txtname.getText().toString();
                etdob = txtdob.getText().toString();
                etchi = chi.getText().toString();
                etgp = gp.getText().toString();

                Boolean addData = DB.updateData(etusername, etname, etdob, etchi, etgp);

                if (addData == true) {
                    Intent intent = new Intent(getApplicationContext(), Profile_view.class);
                    startActivity(intent);
                    Toast.makeText(com.myneuro.ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(com.myneuro.ProfileActivity.this, "Don't leave fields blank, add unknown if not known", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

            /*public void userProfile(){
                this.username = etusername;
                this.name = etname;
                this.dob = etdob;
                this.CHI = etchi;
                this.GP = etgp;
            }

            public String getUsername(){
                return this.username;
            }

             public String getName(){
                return this.name;
            }

            public String getdob(){
                 return this.dob;
            }

            public String getCHI(){
                return this.CHI;
            }

            public String getGp(){
                return this.GP;
            }*/
}

