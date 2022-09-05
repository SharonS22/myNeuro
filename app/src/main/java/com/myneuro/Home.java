package com.myneuro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

;

public class Home extends AppCompatActivity {
    DBHelper_MyNeuro DB;
    TextView seizure, profile, setting, signout, txtseizureDate;
    ImageButton ibtnsetting, ibtnsignout;
    ImageView rect;
    Button btnprofile;
    ConstraintLayout homelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        DB = new DBHelper_MyNeuro(this);
        seizure = (TextView) findViewById(R.id.txtseizure);
        profile = (TextView) findViewById(R.id.txtprofile);
        setting = (TextView) findViewById(R.id.txtsetting);
        signout = (TextView) findViewById(R.id.txtsignout);
        ibtnsetting = (ImageButton) findViewById(R.id.setting);
        ibtnsignout = (ImageButton) findViewById(R.id.signout);
        btnprofile = (Button) findViewById(R.id.btnprofile);
        txtseizureDate = findViewById(R.id.txtseizureDate);
        //btneditprofile = (Button) findViewById(R.id.btneditprofile);
        rect = (ImageView) findViewById(R.id.rect);
        homelayout = (ConstraintLayout) findViewById(R.id.homelayout);

        //load_setting();

        //Last Seizure Date
        Cursor cursor = DB.lastSeizure();
        if(cursor !=null && cursor.moveToFirst()) {
            String date = cursor.getString(cursor.getColumnIndex(DBHelper_MyNeuro.COLUMN_SEIZURE_DATE));

            txtseizureDate.setText((CharSequence) date);
            txtseizureDate.setFocusable(false);
            txtseizureDate.setClickable(false);
        }
        else {
            txtseizureDate.setText("NO ENTRY");
        }

        //View profile button
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile_view.class);
                startActivity(intent);
            }
        });

        //Setting page button
        ibtnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

            }
        });

        //Sign Out button
        ibtnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    showtbDialog();
                }
            }

        });

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

    //Exit dialog box
    private void showtbDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "You Pressed Yes", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Home.this, LoginActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                startActivity(myIntent);
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "You Pressed No", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

        /*private void load_setting(){

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            boolean chk_night = sp.getBoolean("night", false);
            if (chk_night) {
              rect.setBackgroundColor(Color.parseColor("#FF000000"));
              seizure.setTextColor(Color.parseColor("#FFFFFFFF"));
              profile.setTextColor(Color.parseColor("#FFFFFFFF"));
              setting.setTextColor(Color.parseColor("#FFFFFFFF"));
              signout.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else {
                rect.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                seizure.setTextColor(Color.parseColor("#FF000000"));
                profile.setTextColor(Color.parseColor("#FF000000"));
                setting.setTextColor(Color.parseColor("#FF000000"));
                signout.setTextColor(Color.parseColor("#FF000000"));
            }
        }*/

    @Override
    protected void onResume() {
        //load_setting();
        super.onResume();
    }
}
