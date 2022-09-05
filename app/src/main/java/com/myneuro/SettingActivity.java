package com.myneuro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {
    DBHelper_MyNeuro DB;
    TextView accsetting, btnReset, btndeleteacc, btnabout, txtabout;
    ImageView info, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DB = new DBHelper_MyNeuro(this);
        accsetting = (TextView) findViewById(R.id.accsetting);
        btnReset = (TextView) findViewById(R.id.btnReset);
        btnabout = (TextView) findViewById(R.id.btnabout);
        txtabout = (TextView) findViewById(R.id.txtAbout);
        btndeleteacc = (TextView) findViewById(R.id.btndeleteacc);
        info = (ImageView) findViewById(R.id.info);
        delete = (ImageView) findViewById(R.id.delete);

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

        //Password reset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                startActivity(intent);
            }
        });

        //Delete account button
        btndeleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    showtbDialog();
                }
            }

        });

        //About page button
        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }

        });
    }

    //Dialog box for delete account
    private void showtbDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete your account?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DB.deleteData();
                Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}